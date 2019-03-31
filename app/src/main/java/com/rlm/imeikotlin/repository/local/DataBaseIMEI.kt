package com.rlm.imeikotlin.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rlm.imeikotlin.repository.local.converter.DateConverter
import com.rlm.imeikotlin.repository.local.dao.*
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity
import com.rlm.imeikotlin.repository.local.entity.InformacionEntity
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.local.entity.PlantelEntity
import com.rlm.imeikotlin.repository.local.view.DetalleAlumnoView
import com.rlm.imeikotlin.utils.DATABASE_VERSION

@Database(entities = [AlumnoEntity::class,
    OpcionEstudioEntity::class,
    PlantelEntity::class,
    InformacionEntity::class],
    views = arrayOf(DetalleAlumnoView::class),
    version = DATABASE_VERSION,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DataBaseIMEI : RoomDatabase() {
    // --- DAO ---
    abstract fun alumnoDao(): AlumnoDao
    abstract fun opcionEstudioDao(): OpcionEstudioDao
    abstract fun plantelDao(): PlantelDao
    abstract fun informacionDao(): InformacionDao
    abstract fun detalleAlumnoViewDao(): DetalleAlumnoViewDao
}