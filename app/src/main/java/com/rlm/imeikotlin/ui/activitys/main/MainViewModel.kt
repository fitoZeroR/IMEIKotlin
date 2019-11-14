package com.rlm.imeikotlin.ui.activitys.main

import androidx.lifecycle.*
import com.rlm.imeikotlin.data.repository.MainRepository
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val allInformationMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val getAllInformationResourceLiveData = allInformationMutableLiveData.switchMap { mainRepository.loadAllInformation }

    /*fun loadAllInformation(fetchAllInformation: Boolean) {
        if (allInformationMutableLiveData.value == fetchAllInformation) {
            return
        }
        allInformationMutableLiveData.value = fetchAllInformation
    }*/

    private val descargaArchivoMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val getDownloadFileResourceLiveData = descargaArchivoMutableLiveData.switchMap { mainRepository.downloadFile }

    fun downloadPdfOnFromServer(fetchDownloadFile: Boolean) = apply {
        descargaArchivoMutableLiveData.value = fetchDownloadFile
    }

    private val cambiaImagenMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val postCambiaImagenResponseResourceLiveData = cambiaImagenMutableLiveData.switchMap { mainRepository.changeImage(it) }

    fun changePictureOnFromServer(base64: String) = apply {
        cambiaImagenMutableLiveData.value = base64
    }

    fun deleteAlumno() : Boolean {
        if (mainRepository.cleanLogin() != 0) {
            return true
        }
        return false
    }
}