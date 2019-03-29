package com.rlm.imeikotlin.ui.activitys.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.MainRepository
import com.rlm.imeikotlin.repository.local.view.DetalleAlumnoView
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    @VisibleForTesting
    private val allInformationMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllInformationResourceLiveData: LiveData<Resource<List<DetalleAlumnoView>>>

    init {
        getAllInformationResourceLiveData = Transformations.switchMap(allInformationMutableLiveData) {
            if (it == false) AbsentLiveData.create()
            else mainRepository.loadAllInformation()
        }
    }

    fun loadAllInformation(fetchAllInformation: Boolean) {
        if (allInformationMutableLiveData.value == fetchAllInformation) {
            return
        }
        allInformationMutableLiveData.value = fetchAllInformation
    }
}