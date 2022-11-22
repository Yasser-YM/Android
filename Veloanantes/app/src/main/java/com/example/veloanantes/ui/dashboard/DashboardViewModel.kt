package com.example.veloanantes.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.veloanantes.model.Parking


class DashboardViewModel : ViewModel() {

    private val _parkings = MutableLiveData<List<Parking>>().apply {
        value = ArrayList()
    }
    val parkings: MutableLiveData<List<Parking>> = _parkings
}