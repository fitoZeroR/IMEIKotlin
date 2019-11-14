package com.rlm.imeikotlin.data.repository

import com.rlm.imeikotlin.data.local.dao.PlantelDao
import com.rlm.imeikotlin.data.local.entity.PlantelEntity
import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantelesRepository
@Inject
constructor(
    private val plantelDao: PlantelDao,
    private val imeiRemoteDataSource: ImeiRemoteDataSource
) {

    /*fun loadAllPlanteles(): LiveData<Resource<List<PlantelEntity>>> =
        object : DetailNetworkResource<List<PlantelEntity>, InformacionPlantelesResponse>(appExecutors) {
            override fun saveCallResult(item: InformacionPlantelesResponse) =
                item.planteles.forEach {
                    plantelDao.savePlantel(PlantelEntity(it.nombre, it.latitud, it.longitud))
                }

            override fun shouldFetch(data: List<PlantelEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<PlantelEntity>> =
                plantelDao.getAllPlanteles()

            override fun createCall(): LiveData<ApiResponse<InformacionPlantelesResponse>> =
                iRetrofitService.obtienePlanteles()

        }.asLiveData()*/

    val loadAllPlanteles = resultLiveData(
        databaseQuery = { plantelDao.getAllPlanteles() },
        networkCall = { imeiRemoteDataSource.fetchDataGetCampus() },
        saveCallResult = {
            val listPlantelEntity: MutableList<PlantelEntity> = mutableListOf()
            it.planteles.forEach { plantel ->
                listPlantelEntity.add(PlantelEntity(plantel.nombre, plantel.latitud, plantel.longitud))
            }
            plantelDao.savePlantel(listPlantelEntity) })
}