package com.rlm.imeikotlin.ui.activitys.planteles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.rlm.imeikotlin.data.repository.PlantelesRepository
import javax.inject.Inject

class PlantelesViewModel
@Inject
constructor(private val plantelesRepository: PlantelesRepository) : ViewModel() {
    /*@VisibleForTesting
    private val allPlantelesMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllOPlantelesResourceLiveData: LiveData<Resource<List<PlantelEntity>>>

    init {
        getAllOPlantelesResourceLiveData = Transformations.switchMap(allPlantelesMutableLiveData) {
            if (it == false) AbsentLiveData.create()
            else plantelesRepository.loadAllPlanteles()
        }
    }

    fun loadAllPlanteles(fetchAllPlanteles: Boolean) {
        if (allPlantelesMutableLiveData.value == fetchAllPlanteles) {
            return
        }
        allPlantelesMutableLiveData.value = fetchAllPlanteles
    }*/

    //private val allPlantelesMutableLiveData: LiveData<Boolean> = MutableLiveData()
    private val allPlantelesMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val getAllOPlantelesResourceLiveData = allPlantelesMutableLiveData.switchMap { plantelesRepository.loadAllPlanteles }

    fun loadAllPlanteles(fetchAllPlanteles: Boolean) = apply {
        allPlantelesMutableLiveData.value = fetchAllPlanteles
    }
}