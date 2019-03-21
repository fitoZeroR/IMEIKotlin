package com.rlm.imeikotlin.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rlm.imeikotlin.repository.local.converter.DateConverter
import com.rlm.imeikotlin.repository.local.dao.LoginDao
import com.rlm.imeikotlin.repository.local.dao.OpcionDao
import com.rlm.imeikotlin.repository.local.entity.LoginEntity
import com.rlm.imeikotlin.utils.DATABASE_VERSION

@Database(entities = [LoginEntity::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DataBaseIMEI : RoomDatabase() {
    // --- DAO ---
    abstract fun loginDao(): LoginDao
    abstract fun opcionDao(): OpcionDao
}