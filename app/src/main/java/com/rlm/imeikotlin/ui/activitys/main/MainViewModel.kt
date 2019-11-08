package com.rlm.imeikotlin.ui.activitys.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.data.MainRepository
import com.rlm.imeikotlin.data.local.view.DetalleAlumnoView
import com.rlm.imeikotlin.data.remote.model.response.DescargaBoletaResponse
import com.rlm.imeikotlin.data.remote.model.response.FotoResponse
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.data.Resource
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    @VisibleForTesting
    private val allInformationMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllInformationResourceLiveData: LiveData<Resource<List<DetalleAlumnoView>>>

    @VisibleForTesting
    private val descargaArchivoMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getDownloadFileResourceLiveData: LiveData<Resource<DescargaBoletaResponse>>

    @VisibleForTesting
    private val cambiaImagenMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var postCambiaImagenResponseResourceLiveData: LiveData<Resource<FotoResponse>>

    init {
        getAllInformationResourceLiveData = Transformations.switchMap(allInformationMutableLiveData) {
            if (it == false) AbsentLiveData.create()
            else mainRepository.loadAllInformation()
        }

        getDownloadFileResourceLiveData = Transformations.switchMap(descargaArchivoMutableLiveData) {
            if (it == false) AbsentLiveData.create()
            else mainRepository.downloadFileOnFromServer()
        }

        postCambiaImagenResponseResourceLiveData = Transformations.switchMap(cambiaImagenMutableLiveData) {
            if (it == null) AbsentLiveData.create()
            else mainRepository.changeImageOnFromServer(it)
        }
    }

    fun loadAllInformation(fetchAllInformation: Boolean) {
        if (allInformationMutableLiveData.value == fetchAllInformation) {
            return
        }
        allInformationMutableLiveData.value = fetchAllInformation
    }

    fun changePictureOnFromServer(base64: String) {
        cambiaImagenMutableLiveData.value = base64
    }

    fun downloadPdfOnFromServer(fetchDownloadFile: Boolean) {
        descargaArchivoMutableLiveData.value = fetchDownloadFile
    }

    fun deleteAlumno() : Boolean {
        if (mainRepository.cleanLogin() != 0) {
            return true
        }
        return false
    }
}