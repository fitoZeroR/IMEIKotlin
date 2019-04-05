package com.rlm.imeikotlin.utils

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.os.VibrationEffect
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rlm.imeikotlin.R
import org.apache.commons.io.FileUtils
import org.jetbrains.anko.vibrator
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
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
                val arregloFecha = if (fecha.length > 10) fecha.substring(
                    0,
                    10
                ).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() else fecha.split(
                    "-".toRegex()
                ).dropLastWhile { it.isEmpty() }.toTypedArray()

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, Integer.parseInt(arregloFecha[0]))
                calendar.set(Calendar.MONTH, Integer.parseInt(arregloFecha[1]) - 1)
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arregloFecha[2]))

                return SimpleDateFormat(formato).format(calendar.time)
            } catch (exception: Exception) {
                return fecha
            }
        }

        fun getImageUri(inImage: Bitmap, context: Context): Uri {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
            return Uri.parse(path)
        }

        fun getFilePathFromContentUri(selectedImageUri: Uri, context: Context): String {
            val filePath: String
            val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)

            val cursor = context.contentResolver.query(selectedImageUri, filePathColumn, null, null, null)
            Objects.requireNonNull(cursor).moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }

        fun archivoBase64(archivo: String?): String {
            var base64 = ""
            if (archivo != null && !archivo.isEmpty() && archivo != "null") {
                val file = File(archivo)
                try {
                    val bytes = FileUtils.readFileToByteArray(file)
                    base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return base64
        }

        fun getAccountNames(context: Context): Array<String?> {
            val mAccountManager = AccountManager.get(context)
            val accounts = mAccountManager.getAccountsByType("com.google")
            val names = arrayOfNulls<String>(accounts.size)
            for (i in names.indices) {
                names[i] = accounts[i].name
            }
            return names
        }
    }
}