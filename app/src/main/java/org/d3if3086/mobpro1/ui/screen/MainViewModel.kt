package org.d3if3086.mobpro1.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.d3if3086.mobpro1.model.Bakso

class MainViewModel : ViewModel() {
    val data = mutableStateListOf<Bakso>()
    var nama: String by mutableStateOf("")
    var deskripsi: String by mutableStateOf("")
    var review: String by mutableStateOf("")

    fun deleteBakso(bakso: Bakso) {
        data.remove(bakso)
    }

    fun addBakso(nama: String, deskripsi: String, review: String) {
        data.add(Bakso(nama, deskripsi, review))
    }
}