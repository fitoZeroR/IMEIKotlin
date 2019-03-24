package com.rlm.imeikotlin.ui.activitys.planteles

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.PlantelesRepository
import com.rlm.imeikotlin.repository.local.entity.PlantelEntity
import com.rlm.imeikotlin.repository.remote.modelo.response.InformacionPlantelesResponse
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class PlantelesViewModel
@Inject
constructor(private val plantelesRepository: PlantelesRepository) : ViewModel() {
    @VisibleForTesting
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
    }
}