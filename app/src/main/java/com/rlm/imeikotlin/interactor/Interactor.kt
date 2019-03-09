package com.rlm.imeikotlin.interactor

import com.rlm.imeikotlin.repository.ws.IRetrofitWS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Interactor @Inject constructor(val iRetrofitWS: IRetrofitWS)