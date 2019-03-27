package com.rlm.imeikotlin.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity

@Dao
interface AlumnoDao {
    @Insert(onConflict = REPLACE)
    fun save(alumnoEntity: AlumnoEntity)

    @Query("SELECT * FROM " + AlumnoEntity.TABLE_NAME_ALUMNO + " WHERE " + AlumnoEntity.COLUMNA_USUARIO + " = :usuario and "
            + AlumnoEntity.COLUMNA_PASSWORD + " = :password")
    fun getAlumno(usuario: String, password: String): LiveData<AlumnoEntity>

    @Query("SELECT COUNT(" + AlumnoEntity.COLUMNA_ID_ALUMNO + ") FROM " + AlumnoEntity.TABLE_NAME_ALUMNO)
    fun getTotalAlumno() : Int

    @Query("DELETE FROM " + AlumnoEntity.TABLE_NAME_ALUMNO)
    fun clearAlumno()
}