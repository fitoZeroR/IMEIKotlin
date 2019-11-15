package com.rlm.imeikotlin.ui.activitys.opciones

import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.data.repository.OpcionesRepository
import javax.inject.Inject

class OpcionesViewModel
@Inject
constructor(opcionesRepository: OpcionesRepository) : ViewModel() {
    val getAllOptionsResourceLiveData = opcionesRepository.loadAllOptions
}