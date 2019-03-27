package com.rlm.imeikotlin.ui.activitys.main

import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.MainRepository
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
}