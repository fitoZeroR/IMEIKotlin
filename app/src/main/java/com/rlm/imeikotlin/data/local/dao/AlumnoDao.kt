package com.rlm.imeikotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rlm.imeikotlin.data.local.entity.AlumnoEntity

@Dao
interface AlumnoDao {
    @Insert(onConflict = REPLACE)
    fun save(alumnoEntity: AlumnoEntity)

    @Query("SELECT * FROM " + AlumnoEntity.TABLE_NAME_ALUMNO)
    fun getAlumno(): AlumnoEntity

    @Query(
        "SELECT * FROM " + AlumnoEntity.TABLE_NAME_ALUMNO + " WHERE " + AlumnoEntity.COLUMNA_USUARIO + " = :usuario and "
                + AlumnoEntity.COLUMNA_PASSWORD + " = :password"
    )
    fun getAlumnoLogin(usuario: String, password: String): LiveData<AlumnoEntity>

    @Query("SELECT COUNT(" + AlumnoEntity.COLUMNA_ID_ALUMNO + ") FROM " + AlumnoEntity.TABLE_NAME_ALUMNO)
    fun getTotalAlumno(): Int

    @Query("DELETE FROM " + AlumnoEntity.TABLE_NAME_ALUMNO)
    fun clearAlumno()

    @Delete
    fun deleteAlumno(alumnoEntity: AlumnoEntity): Int
}