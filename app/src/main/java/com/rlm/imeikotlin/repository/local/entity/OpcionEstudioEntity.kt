package com.rlm.imeikotlin.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = OpcionEstudioEntity.TABLE_NAME_OPCION_ESTUDIO)
data class OpcionEstudioEntity(
    @ColumnInfo(name = COLUMNA_ENCABEZADO)
    val encabezado: String,
    @ColumnInfo(name = COLUMNA_DESCRIPCION)
    val descripcion: String,
    @ColumnInfo(name = COLUMNA_TITULO)
    val titulo: String,
    @ColumnInfo(name = COLUMNA_PLANTELES)
    val planteles: String?,
    @ColumnInfo(name = COLUMNA_LISTA_DIPLOMADOS)
    val listaDiplomados: String) {

    companion object {
        const val TABLE_NAME_OPCION_ESTUDIO = "Opcion_Estudio"
        const val COLUMNA_ENCABEZADO = "Encabezado"
        const val COLUMNA_DESCRIPCION = "Descripcion"
        const val COLUMNA_TITULO = "Titulo"
        const val COLUMNA_PLANTELES = "Planteles"
        const val COLUMNA_LISTA_DIPLOMADOS = "Lista_Diplomados"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}