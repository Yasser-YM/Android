package com.example.veloanantes.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veloanantes.adapter.StationAdapter
import com.example.veloanantes.api.RetrofitHelper
import com.example.veloanantes.api.StationApi
import com.example.veloanantes.databinding.FragmentHomeBinding
import com.example.veloanantes.model.allStation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.create

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView=binding.RecyclerView
        val progressBarStation=binding.progressBarStation

        homeViewModel.stations.observe(viewLifecycleOwner) {

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = StationAdapter(it,requireContext())
            progressBarStation.visibility=View.GONE

            allStation= it
        }

        val stationApi = RetrofitHelper().getInstance().create(StationApi::class.java)
        GlobalScope.launch{
            val result = stationApi.getStations()
            Log.d("Home",result.body().toString())
            homeViewModel.stations.postValue(result.body())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}