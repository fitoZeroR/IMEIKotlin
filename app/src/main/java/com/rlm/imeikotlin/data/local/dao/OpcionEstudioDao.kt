package com.rlm.imeikotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rlm.imeikotlin.data.local.entity.OpcionEstudioEntity

@Dao
interface OpcionEstudioDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveAll(opcionEstudioEntity: List<OpcionEstudioEntity>)

    @Query("SELECT * FROM " + OpcionEstudioEntity.TABLE_NAME_OPCION_ESTUDIO)
    fun getAll(): LiveData<List<OpcionEstudioEntity>>
}