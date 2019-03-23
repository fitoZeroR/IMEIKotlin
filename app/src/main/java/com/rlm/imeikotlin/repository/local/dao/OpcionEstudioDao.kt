package com.rlm.imeikotlin.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity

@Dao
interface OpcionEstudioDao {
    @Insert(onConflict = REPLACE)
    fun save(opcionEstudioEntity: OpcionEstudioEntity)

    @Insert(onConflict = REPLACE)
    fun saveAll(opcionEstudioEntity: List<OpcionEstudioEntity>)

    @Query("SELECT * FROM " + OpcionEstudioEntity.TABLE_NAME_OPCION_ESTUDIO)
    fun getAll(): LiveData<List<OpcionEstudioEntity>>

    @Query("SELECT * FROM " + OpcionEstudioEntity.TABLE_NAME_OPCION_ESTUDIO + " WHERE " + OpcionEstudioEntity.COLUMNA_ENCABEZADO + " = :encabezado")
    fun get(encabezado: String): LiveData<List<OpcionEstudioEntity>>

    @Query("DELETE FROM " + OpcionEstudioEntity.TABLE_NAME_OPCION_ESTUDIO)
    fun clear()
}