package com.rlm.imeikotlin.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.VibrationEffect
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rlm.imeikotlin.R
import org.jetbrains.anko.vibrator
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    companion object {
        fun hideKeyboard(activity: Activity) {
            try {
                val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                Objects.requireNonNull(imm)
                    .hideSoftInputFromWindow(Objects.requireNonNull<View>(activity.currentFocus).getWindowToken(), 0)
            } catch (ignored: Exception) {
            }

        }

        @SuppressLint("NewApi")
        fun informaErrorConexionWifi(activity: Activity, mensaje: String, finalizaActividad: Boolean) {
            activity.vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            mensajeInformativo(
                activity,
                mensaje,
                finalizaActividad
            )
        }

        fun mensajeInformativo(activity: Activity, mensaje: String, finalizaActividad: Boolean) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(mensaje)
            builder.setPositiveButton(activity.getString(R.string.action_accept)) { dialog, which ->
                dialog.dismiss()
                if (finalizaActividad) {
                    activity.finish()
                }
            }
            builder.setCancelable(false)
            builder.create()
            builder.show()
        }

        fun mensajeOpcional(activity: Activity, mensaje: String): AlertDialog.Builder {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setTitle(R.string.app_name)
            alertDialogBuilder.setMessage(mensaje)
            alertDialogBuilder.setNegativeButton(R.string.action_cancel) { dialog, which -> dialog.dismiss() }
            alertDialogBuilder.setCancelable(false)

            return alertDialogBuilder
        }

        fun parsearFechaCumpleanos(fecha: String, formato: String): String {
            try {
                val ArregloFecha = if (fecha.length > 10) fecha.substring(
                    0,
                    10
                ).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() else fecha.split(
                    "-".toRegex()
                ).dropLastWhile { it.isEmpty() }.toTypedArray()

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, Integer.parseInt(ArregloFecha[0]))
                calendar.set(Calendar.MONTH, Integer.parseInt(ArregloFecha[1]) - 1)
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ArregloFecha[2]))

                return SimpleDateFormat(formato).format(calendar.time)
            } catch (exception: Exception) {
                return fecha
            }
        }
    }
}