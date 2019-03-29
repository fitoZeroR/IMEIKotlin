package com.rlm.imeikotlin.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import com.rlm.imeikotlin.repository.local.entity.InformacionEntity

@Dao
interface InformacionDao {
    @Insert(onConflict = IGNORE)
    fun saveInformacion(informacionEntity: InformacionEntity)
}