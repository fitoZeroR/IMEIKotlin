package com.rlm.imeikotlin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import com.rlm.imeikotlin.data.local.entity.InformacionEntity

@Dao
interface InformacionDao {
    @Insert(onConflict = IGNORE)
    fun saveInformacion(informacionEntity: InformacionEntity)
}