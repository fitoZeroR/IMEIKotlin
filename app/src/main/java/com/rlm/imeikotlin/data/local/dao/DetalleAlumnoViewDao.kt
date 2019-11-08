@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.rlm.imeikotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.rlm.imeikotlin.data.local.view.DetalleAlumnoView

@Dao
interface DetalleAlumnoViewDao {
    @Query("SELECT * FROM DetalleAlumnoView")
    fun getDetalleAlumno(): LiveData<List<DetalleAlumnoView>>
}