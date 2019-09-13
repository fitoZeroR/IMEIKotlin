package com.rlm.imeikotlin.repository.local.entity

import androidx.room.*
import com.rlm.imeikotlin.repository.local.entity.embedded.Pago
import com.rlm.imeikotlin.repository.local.entity.embedded.Plan

@Entity(
    tableName = InformacionEntity.TABLE_NAME_INFORMACION,
    foreignKeys = [ForeignKey(
        entity = AlumnoEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf(InformacionEntity.COLUMNA_ID_ALUMNO),
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(
        value = [InformacionEntity.COLUMNA_ID_ALUMNO])]
)
data class InformacionEntity(
    @ColumnInfo(name = COLUMNA_ID_ALUMNO)
    val idAlumno: Int,
    @ColumnInfo(name = COLUMNA_ID_CUATRIMESTRE)
    val idCuatrimestre: String,
    @ColumnInfo(name = COLUMNA_NOMBRE)
    val nombre: String,
    @ColumnInfo(name = COLUMNA_TIPO_INFORMACION)
    val tipoInformacion: String,
    @Embedded
    val plan: Plan?,
    @Embedded
    val pago: Pago?) {


    companion object {
        const val TABLE_NAME_INFORMACION = "Informacion"
        const val COLUMNA_ID_ALUMNO = "Id_Alumno"
        const val COLUMNA_ID_CUATRIMESTRE = "Id_Cuatrimestre"
        const val COLUMNA_NOMBRE= "Nombre_Informacion"
        const val COLUMNA_TIPO_INFORMACION = "Tipo_Informacion"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}