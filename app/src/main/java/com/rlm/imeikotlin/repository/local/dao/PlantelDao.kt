package com.rlm.imeikotlin.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rlm.imeikotlin.repository.local.entity.PlantelEntity

@Dao
interface PlantelDao {
    @Insert(onConflict = REPLACE)
    fun savePlantel(listaPlantelEntity: PlantelEntity)

    @Query("SELECT * FROM " + PlantelEntity.TABLE_NAME_PLANTEL)
    fun getAllPlanteles(): LiveData<List<PlantelEntity>>
}