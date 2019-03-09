package com.rlm.imeikotlin.ui.activity.main

import android.accounts.AccountManager
import android.app.DownloadManager
import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.picasso.PicassoCircleTransformation
import com.rlm.imeikotlin.presenter.MainPresenter
import com.rlm.imeikotlin.repository.modelo.DescargaBoleta
import com.rlm.imeikotlin.repository.modelo.Login
import com.rlm.imeikotlin.repository.modelo.PagosAsignaturas
import com.rlm.imeikotlin.ui.activity.BaseActivity
import com.rlm.imeikotlin.ui.activity.login.LoginActivity
import com.rlm.imeikotlin.ui.activity.main.fragment.AsignaturasFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.directorio.DirectorioFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.EstadisticasFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.PagosFragment
import com.rlm.imeikotlin.utils.*
import com.rlm.imeikotlin.utils.Tools.Companion.mensajeOpcional
import com.rlm.imeikotlin.utils.Tools.Companion.parsearFechaCumpleanos
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_menu_lateral.*
import org.apache.commons.io.FileUtils
import org.jetbrains.anko.selector
import org.jetbrains.anko.wifiManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mainPresenter: MainPresenter
    @Inject
    lateinit var preferencias: SharedPreferences
    /*@Inject
    lateinit var mensajeInicio: String*/

    private var uriImagen: Uri? = null
    private var login: Login? = null
    private var pagosAsignaturas: PagosAsignaturas? = null
    private var picasso: Picasso? = null

    private var selectedFragment: Fragment? = null
    private var pagosFragment: PagosFragment? = null
    private var asignaturasFragment: AsignaturasFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mainPresenter.setView(this)

        //showToast(mensajeInicio)

        val bundle = intent.extras
        if (bundle != null) {
            login = Gson().fromJson<Any>(bundle.getString(BUNDLE_INFORMACION_USUARIO), Login::class.java) as Login?
            pagosAsignaturas = Gson().fromJson<Any>(
                bundle.getString(BUNDLE_INFORMACION_PAGOS_ASIGNATURAS),
                PagosAsignaturas::class.java
            ) as PagosAsignaturas?
        }

        confingToolBar()
        createMenuBottomNavigationView()
        inicializaActividad()
        inicializaMenuLateral()
    }

    override fun onDestroy() {
        mainPresenter.terminate()
        super.onDestroy()
    }

    override fun getLayoutResource() = R.layout.activity_main

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            alertaOpcional(true, getString(R.string.msg_valida_deslogueo))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            uriImagen = data?.data
            alertaOpcional(false, getString(R.string.msg_actualiza_imagen))
        }
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            uriImagen = getImageUri(data?.getParcelableExtra<Bitmap>("data")!!)
            alertaOpcional(false, getString(R.string.msg_actualiza_imagen))
        }
    }

    override fun compartirBoleta(descargaBoleta: DescargaBoleta) {
        // registrer receiver in order to verify when download is complete
        registerReceiver(DonwloadCompleteReceiver(), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        val request = DownloadManager.Request(Uri.parse(descargaBoleta.data.boletaUrl))
        request.setDescription("Descargando Archivo: $NOMBRE_ARCHIVO_PDF")
        request.setTitle("Descargando")
        // in order for this if to run, you must use the android 3.2 to compile your app
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //}
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, NOMBRE_ARCHIVO_PDF)

        // get download service and enqueue file
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        Objects.requireNonNull(manager).enqueue(request)
    }

    private inner class DonwloadCompleteReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                // DO SOMETHING WITH THIS FILE
                //Obtiene la Uri del recurso.
                val uri = Uri.fromFile(
                    File(
                        Environment.getExternalStorageDirectory().absolutePath + RUTA_ARCHIVO_PDF,
                        NOMBRE_ARCHIVO_PDF
                    )
                )
                //Crea intent para enviar el email.
                val intentEnvioArchivo = Intent(Intent.ACTION_SEND)
                intentEnvioArchivo.type = MIME_PDF
                //Agrega email o emails de destinatario.
                val accountNames = getAccountNames()
                intentEnvioArchivo.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(if (accountNames.size == 0) "" else accountNames[0])
                )
                intentEnvioArchivo.putExtra(Intent.EXTRA_SUBJECT, "Envio de archivo PDF.")
                intentEnvioArchivo.putExtra(Intent.EXTRA_TEXT, "Hola te envío un archivo PDF.")
                intentEnvioArchivo.putExtra(Intent.EXTRA_STREAM, uri)

                val builder = StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivity(Intent.createChooser(intentEnvioArchivo, getString(R.string.msg_compartir_archivo)))
            }
        }
    }

    private fun inicializaActividad() {
        title = getString(R.string.title_fragment_pagos)
        pagosFragment = PagosFragment.initInstance(pagosAsignaturas!!.data.pagos)
        addFragment(pagosFragment!!)
        asignaturasFragment = AsignaturasFragment.initInstance(pagosAsignaturas!!.data.plan)
        asignaEventos()
    }

    private fun asignaEventos() {
        RxTextView.textChanges(filtroEdt_id)
            .subscribe {
                if (it.toString().length > 0) {
                    if (selectedFragment is PagosFragment || selectedFragment == null) {
                        pagosFragment?.filtro(it.toString())
                    } else if (selectedFragment is AsignaturasFragment) {
                        asignaturasFragment?.filtro(it.toString())
                    }
                }
            }

        RxView.clicks(txv_cerrar_sesion_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                drawer_layout.closeDrawer(GravityCompat.START)
                alertaOpcional(true, getString(R.string.msg_valida_deslogueo))
            }

        RxView.clicks(txv_descarga_boleta_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (wifiManager.isWifiEnabled()) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    comparteArchivo()
                } else {
                    Tools.informaErrorConexionWifi(
                        this@MainActivity,
                        getString(R.string.msg_no_conexion_internet),
                        false
                    )
                }
            }

        RxView.clicks(imv_camara_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { seleccionaImagen() }
    }

    private fun createMenuBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener({
            when (it.getItemId()) {
                R.id.menu_pagos_item -> {
                    selectedFragment = PagosFragment.newInstance()
                    title = getString(R.string.title_fragment_pagos)
                    filtroEdt_id.setVisibility(View.VISIBLE)
                }
                R.id.menu_asignaturas_item -> {
                    selectedFragment = AsignaturasFragment.newInstance()
                    title = getString(R.string.title_fragment_asignaturas)
                    filtroEdt_id.setVisibility(View.VISIBLE)
                }
                R.id.menu_estadisticas_item -> {
                    selectedFragment = EstadisticasFragment.newInstance(
                        pagosAsignaturas?.data?.pagos!!,
                        pagosAsignaturas?.data?.plan!!
                    )
                    title = getString(R.string.title_fragment_estadisticas)
                    filtroEdt_id.setVisibility(View.GONE)
                }
                R.id.menu_directorio_item -> {
                    selectedFragment = DirectorioFragment.newInstance()
                    title = getString(R.string.title_fragment_directorio)
                    filtroEdt_id.setVisibility(View.GONE)
                }
            }
            selectedFragment?.let { addFragment(it) }
            limpiaFiltro()
            true
        })
    }

    private fun inicializaMenuLateral() {
        txv_matricula_id.setText(login?.let { it.data.alumno.matricula } )
        txv_nombre_completo_id.setText(login?.let { it.data.alumno.nombre } + " " + login?.let { it.data.alumno.paterno } + " " + login?.let { it.data.alumno.materno })
        txv_fecha_nacimiento_id.setText(
            parsearFechaCumpleanos(
                login?.let { it.data.alumno.nacimiento }!!,
                FORMATO_CUMPLEANOS
            )
        )
        txv_curp_id.setText(login?.let { it.data.alumno.curp })
        txv_telefono_id.setText(login?.let { it.data.alumno.telefono })
        txv_carrera_id.setText(login?.let { it.data.alumno.licenciatura })
        txv_plantel_id.setText(login?.let { it.data.alumno.plantel })
        txv_cuatrimestre_id.setText(login?.let { it.data.alumno.cuatrimestre + " Cuatrimestre" })

        //picasso = Picasso.with(getApplicationContext());
        picasso = Picasso.get()
        obtieneImagenUsuario()
    }

    private fun obtieneImagenUsuario() {
        if (uriImagen != null) {
            picasso?.let { it.load(uriImagen).transform(PicassoCircleTransformation()).into(imv_estudiante_id) }
        //} else if (login?.data?.alumno?.foto == null || login?.data?.alumno?.foto.equals("")) {
        } else if (login?.let { it.data.alumno.foto } == null || login?.let { it.data.alumno.foto.equals("") }!!) {
            picasso?.let { it.load(R.drawable.silueta_usuario).transform(PicassoCircleTransformation()).into(imv_estudiante_id) }
        } else {
            picasso?.let { it.load(login?.let { it.data.alumno.foto }).transform(PicassoCircleTransformation()).placeholder(R.drawable.silueta_usuario)
                .error(R.drawable.silueta_usuario).into(imv_estudiante_id) }
            //.into((ImageView) findViewById(R.id.imv_estudiante_id));
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun alertaOpcional(bandera: Boolean, mensaje: String) {
        mensajeOpcional(this, mensaje)
            .setPositiveButton(R.string.action_accept, { dialog, which ->
                if (bandera) {
                    val editorPreferencias = preferencias.edit()
                    editorPreferencias.putString(MATRICULA_SP, "")
                    editorPreferencias.putString(PASSWORD, "")
                    editorPreferencias.putString(JSON_LOGIN, "")
                    editorPreferencias.putString(JSON_ASIGNATURAS_PAGOS, "")
                    editorPreferencias.commit()

                    navigate<LoginActivity>()
                    finish()
                } else {
                    if (wifiManager.isWifiEnabled()) {
                        obtieneImagenUsuario()
                        mainPresenter.enviarImagen(
                            login?.let { it.data.tokenSesion }!!,
                            archivoBase64(uriImagen?.let { getFilePathFromContentUri(it) })
                        )
                    } else {
                        Tools.informaErrorConexionWifi(
                            this@MainActivity,
                            getString(R.string.msg_no_conexion_internet),
                            false
                        )
                    }
                }
            }).show()
    }

    private fun getAccountNames(): Array<String?> {
        val mAccountManager = AccountManager.get(this)
        val accounts = mAccountManager.getAccountsByType("com.google")
        val names = arrayOfNulls<String>(accounts.size)
        for (i in names.indices) {
            names[i] = accounts[i].name
        }
        return names
    }

    private fun archivoBase64(archivo: String?): String {
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

    private fun getFilePathFromContentUri(selectedImageUri: Uri): String {
        val filePath: String
        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)

        val cursor = contentResolver.query(selectedImageUri, filePathColumn, null, null, null)
        Objects.requireNonNull(cursor).moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

    private fun limpiaFiltro() = filtroEdt_id.setText("")

    private fun seleccionaImagen() {

        selector(getString(R.string.msg_seleccione_opcion), resources.getStringArray(R.array.lista_opcion_foto).toList(), { dialogInterface, i ->
            var iPicture: Intent? = null
            var code = TAKE_PICTURE
            when (i) {
                0 -> iPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                1 -> {
                    iPicture = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    iPicture.type = "image/*"
                    code = SELECT_PICTURE
                }
            }
            startActivityForResult(iPicture, code)
            dialogInterface.dismiss()

        })
    }

    private fun comparteArchivo() {
        mainPresenter.descargaBoleta(login!!.data.tokenSesion)
    }
}

/*
In some cases like this:

if(something != null && something.thingy) {
    ...
} else {
    ...
}
You can replace that logic with

something?.takeIf { it.thingy }?.let {
   ...
} ?: {
   ...
}
 */