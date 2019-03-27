package com.rlm.imeikotlin.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rlm.imeikotlin.repository.local.converter.DateConverter
import com.rlm.imeikotlin.repository.local.dao.AlumnoDao
import com.rlm.imeikotlin.repository.local.dao.OpcionEstudioDao
import com.rlm.imeikotlin.repository.local.dao.PlantelDao
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.local.entity.PlantelEntity
import com.rlm.imeikotlin.utils.DATABASE_VERSION

@Database(entities = [AlumnoEntity::class, OpcionEstudioEntity::class, PlantelEntity::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DataBaseIMEI : RoomDatabase() {
    // --- DAO ---
    abstract fun alumnoDao(): AlumnoDao
    abstract fun opcionEstudioDao(): OpcionEstudioDao
    abstract fun plantelDao(): PlantelDao
}