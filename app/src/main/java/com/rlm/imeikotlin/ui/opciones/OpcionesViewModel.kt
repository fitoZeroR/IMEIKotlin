package com.rlm.imeikotlin.ui.opciones

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.OpcionesRepository
import com.rlm.imeikotlin.repository.local.entity.OpcionEntity
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class OpcionesViewModel
@Inject
constructor(private val opcionesRepository: OpcionesRepository) : ViewModel() {
    @VisibleForTesting
    private val allOptionsMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllOptionsResourceLiveData: LiveData<Resource<List<OpcionEntity>>>

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