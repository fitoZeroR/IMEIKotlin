package com.rlm.imeikotlin.ui.activitys.main

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun providePicasso(): Picasso = Picasso.get()
}