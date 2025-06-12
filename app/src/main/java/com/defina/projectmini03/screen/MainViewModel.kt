package com.defina.projectmini03.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.defina.projectmini03.model.Peminjaman
import com.defina.projectmini03.network.ListApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Peminjaman>())
    private set

    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = ListApi.service.getList()
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}