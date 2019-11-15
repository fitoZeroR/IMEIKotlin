package com.rlm.imeikotlin.ui.activitys.planteles

import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.data.repository.PlantelesRepository
import javax.inject.Inject

class PlantelesViewModel
@Inject
constructor(plantelesRepository: PlantelesRepository) : ViewModel() {
    val getAllOPlantelesResourceLiveData = plantelesRepository.loadAllPlanteles
}