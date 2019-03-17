package com.rlm.imeikotlin.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rlm.imeikotlin.repository.local.converter.DateConverter
import com.rlm.imeikotlin.utils.DATABASE_VERSION

//@Database(entities = {  }, version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MyDataBase : RoomDatabase() {
    // --- SINGLETON ---
    //@Volatile
    //private var INSTANCE: MyDataBase? = null

    // --- DAO ---
    /*abstract fun favoritoDao(): FavoritoDAO
     abstract fun userDao(): UserDao
    abstract fun placeDAO(): PlaceDAO*/
}