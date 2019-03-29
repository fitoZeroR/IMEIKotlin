@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.rlm.imeikotlin.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.rlm.imeikotlin.repository.local.view.DetalleAlumnoView

@Dao
interface DetalleAlumnoViewDao {
    @Query("SELECT * FROM DetalleAlumnoView")
    fun getDetalleAlumno(): LiveData<List<DetalleAlumnoView>>
}