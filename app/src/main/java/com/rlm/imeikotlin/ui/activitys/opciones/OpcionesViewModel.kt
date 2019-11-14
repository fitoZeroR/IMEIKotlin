package com.rlm.imeikotlin.ui.activitys.opciones

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.data.repository.OpcionesRepository
import com.rlm.imeikotlin.data.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.data.Resource
import javax.inject.Inject

class OpcionesViewModel
@Inject
constructor(private val opcionesRepository: OpcionesRepository) : ViewModel() {
    @VisibleForTesting
    private val allOptionsMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllOptionsResourceLiveData: LiveData<Resource<List<OpcionEstudioEntity>>>

    init {
        getAllOptionsResourceLiveData = Transformations.switchMap(allOptionsMutableLiveData) {
            if (it == false) AbsentLiveData.create()
            else opcionesRepository.loadAllOptions()
        }
    }

    fun loadAllOptions(fetchAllOptions: Boolean) {
        if (allOptionsMutableLiveData.value == fetchAllOptions) {
            return
        }
        allOptionsMutableLiveData.value = fetchAllOptions
    }
}