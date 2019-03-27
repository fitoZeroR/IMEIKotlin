package com.rlm.imeikotlin.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AlumnoEntity.TABLE_NAME_ALUMNO)
data class AlumnoEntity(
    @ColumnInfo(name = COLUMNA_TOKEN_SESION)
    val tokenSesion: String?,
    @ColumnInfo(name = COLUMNA_USUARIO)
    val usuario: String,
    @ColumnInfo(name = COLUMNA_PASSWORD)
    val password: String,
    @ColumnInfo(name = COLUMNA_ID_ALUMNO)
    val idAlumno: String?,
    @ColumnInfo(name = COLUMNA_ID_LICENCIATURA)
    val idLicenciatura: String?,
    @ColumnInfo(name = COLUMNA_ID_PLANTEL)
    val idPlantel : String?,
    @ColumnInfo(name = COLUMNA_NOMBRE)
    val nombre : String?,
    @ColumnInfo(name = COLUMNA_APELLIDO_PATERNO)
    val apellidoPaterno : String?,
    @ColumnInfo(name = COLUMNA_APELLIDO_MATERNO)
    val apellidoMaterno : String?,
    @ColumnInfo(name = COLUMNA_FECHA_NACIMIENTO)
    val fechaNacimiento : String?,
    @ColumnInfo(name = COLUMNA_LICENCIATURA)
    val licenciatura : String?,
    @ColumnInfo(name = COLUMNA_MATRICULA)
    val matricula : String?,
    @ColumnInfo(name = COLUMNA_CURP)
    val curp : String?,
    @ColumnInfo(name = COLUMNA_FOTO)
    val foto : String?,
    @ColumnInfo(name = COLUMNA_CUATRIMESTRE)
    val cuatrimestre : String?,
    @ColumnInfo(name = COLUMNA_PLANTEL)
    val plantel : String?,
    @ColumnInfo(name = COLUMNA_TELEFONO)
    val telefono : String?,
    @ColumnInfo(name = COLUMNA_MENSAJE)
    val mensaje : String) {

    companion object {
        const val TABLE_NAME_ALUMNO = "Login"
        const val COLUMNA_TOKEN_SESION = "Token_Sesion"
        const val COLUMNA_USUARIO = "Usuario"
        const val COLUMNA_PASSWORD = "Password"
        const val COLUMNA_ID_ALUMNO = "Id_Alumno"
        const val COLUMNA_ID_LICENCIATURA = "Id_Licenciatura"
        const val COLUMNA_ID_PLANTEL = "Id_Plantel"
        const val COLUMNA_NOMBRE = "Nombre"
        const val COLUMNA_APELLIDO_PATERNO = "Apellido_Paterno"
        const val COLUMNA_APELLIDO_MATERNO = "Apellido_Materno"
        const val COLUMNA_FECHA_NACIMIENTO = "Fecha_Nacimiento"
        const val COLUMNA_LICENCIATURA = "Licenciatura"
        const val COLUMNA_MATRICULA = "Matricula"
        const val COLUMNA_CURP = "CURP"
        const val COLUMNA_FOTO = "Foto"
        const val COLUMNA_CUATRIMESTRE = "Cuatrimestre"
        const val COLUMNA_PLANTEL = "Plantel"
        const val COLUMNA_TELEFONO = "Telefono"
        const val COLUMNA_MENSAJE = "Mensaje"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}