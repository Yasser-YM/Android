package com.example.veloanantes.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veloanantes.adapter.ParkingAdapter
import com.example.veloanantes.api.ParkingApi

import com.example.veloanantes.api.RetrofitHelper
import com.example.veloanantes.databinding.FragmentDashboardBinding


import com.example.veloanantes.model.allparking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView=binding.RecyclerView
        val progressBarParking=binding.progressBarParking

        dashboardViewModel.parkings.observe(viewLifecycleOwner) {

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = ParkingAdapter(it,requireContext())
            progressBarParking.visibility=View.GONE

            allparking = it
        }

        val parkingApi = RetrofitHelper().getInstance().create(ParkingApi::class.java)
        GlobalScope.launch{
            val result = parkingApi.getparkings()
            Log.d("Dashboard",result.body().toString())
            dashboardViewModel.parkings.postValue(result.body())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}