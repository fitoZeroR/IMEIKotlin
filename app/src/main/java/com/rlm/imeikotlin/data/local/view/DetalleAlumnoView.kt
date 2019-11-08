package com.rlm.imeikotlin.data.local.view

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT Id_Licenciatura AS idLicenciatura, Id_Plantel AS idPlantel, Nombre AS nombre, " +
            "Apellido_Paterno AS apellidoPaterno, Apellido_Materno AS apellidoMaterno, Fecha_Nacimiento AS fechaNacimiento, " +
            "Licenciatura AS licenciatura, Matricula AS matricula, CURP AS curp, Foto AS foto, Cuatrimestre AS cuatrimestre, " +
            "Plantel AS plantel, Telefono AS telefono, Mensaje AS mensaje, Id_Cuatrimestre AS idCuatrimestre, " +
            "Nombre_Informacion AS nombreInformacion, idMateria, materia, Estatus_Plan AS estatusPlan, pago, concepto, " +
            "Estatus_Pago AS estatusPago, Tipo_Informacion AS tipoInformacion FROM Informacion " +
            "INNER JOIN Alumno ON Informacion.Id_Alumno = Alumno.id"
)
data class DetalleAlumnoView(
    val idLicenciatura: String?,
    val idPlantel: String?,
    val nombre: String?,
    val apellidoPaterno: String?,
    val apellidoMaterno: String?,
    val fechaNacimiento: String?,
    val licenciatura: String?,
    val matricula: String?,
    val curp: String?,
    val foto: String?,
    val cuatrimestre: String?,
    val plantel: String?,
    val telefono: String?,
    val mensaje: String,
    val idCuatrimestre: String,
    val nombreInformacion: String,
    val idMateria: String?,
    val materia: String?,
    val estatusPlan: String?,
    val pago: String?,
    val concepto: String?,
    val estatusPago: String?,
    val tipoInformacion: String
)