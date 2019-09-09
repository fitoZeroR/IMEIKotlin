package com.rlm.imeikotlin.ui.activitys.main

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.picasso.PicassoCircleTransformation
import com.rlm.imeikotlin.ui.activitys.BaseActivity
import com.rlm.imeikotlin.ui.activitys.login.LoginActivity
import com.rlm.imeikotlin.utils.*
import com.rlm.imeikotlin.utils.Tools.Companion.mensajeOpcional
import com.rlm.imeikotlin.utils.Tools.Companion.parsearFechaCumpleanos
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_menu_lateral.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.wifiManager
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.lifecycle.Observer
import com.rlm.imeikotlin.repository.local.view.DetalleAlumnoView
import com.rlm.imeikotlin.repository.remote.model.response.*
import com.rlm.imeikotlin.ui.activity.main.fragment.AsignaturasFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.EstadisticasFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.PagosFragment
import com.rlm.imeikotlin.ui.activity.main.fragment.directorio.DirectorioFragment
import com.rlm.imeikotlin.utils.Tools.Companion.archivoBase64
import com.rlm.imeikotlin.utils.Tools.Companion.getAccountNames
import com.rlm.imeikotlin.utils.Tools.Companion.getFilePathFromContentUri
import com.rlm.imeikotlin.utils.Tools.Companion.getImageUri
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*

class MainActivity : BaseActivity(), HasAndroidInjector {
    //@Inject
    //lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var picasso: Picasso

    private var uriImagen: Uri? = null

    private lateinit var listaDetalleAlumnoView: List<DetalleAlumnoView>
    private val pagos: MutableList<Pagos> = mutableListOf()
    private val plan: MutableList<Plan> = mutableListOf()

    private var selectedFragment: Fragment? = null
    private lateinit var pagosFragment: PagosFragment
    private lateinit var asignaturasFragment: AsignaturasFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        subscribeToMainModel()

        mainViewModel.loadAllInformation(true)
    }

    override fun getLayoutResource() = R.layout.activity_main

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    //override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

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
            uriImagen = data!!.data
            alertaOpcional(false, getString(R.string.msg_actualiza_imagen))
        }
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            uriImagen = getImageUri(data?.getParcelableExtra<Bitmap>("data")!!, this@MainActivity)
            alertaOpcional(false, getString(R.string.msg_actualiza_imagen))
        }
    }

    private fun subscribeToMainModel() {
        mainViewModel.getAllInformationResourceLiveData.observe(this, Observer {
            administraObserverResources(it) {
                inicializaVariables(it.data!!)
                confingToolBar()
                inicializaMenuLateral()
                createMenuBottomNavigationView()
                inicializaActividad()
                asignaEventos()
            }
        })

        mainViewModel.getDownloadFileResourceLiveData.observe(this, Observer {
            administraObserverResources(it) {
                compartirBoleta(it.data!!)
            }
        })

        mainViewModel.postCambiaImagenResponseResourceLiveData.observe(this, Observer {
            administraObserverResources(it) {
                showToast(it.data!!.message)
            }
        })
    }

    private fun compartirBoleta(descargaBoletaResponse: DescargaBoletaResponse) {
        // registrer receiver in order to verify when download is complete
        registerReceiver(DonwloadCompleteReceiver(), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        val request = DownloadManager.Request(Uri.parse(descargaBoletaResponse.data.boletaUrl))
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
                val accountNames = getAccountNames(this@MainActivity)
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

    // Este metodo
    private fun inicializaVariables(dataAlumnoList: List<DetalleAlumnoView>) {
        listaDetalleAlumnoView = dataAlumnoList

        val listaEstatusPago: MutableList<MutableList<EstatusPago>> = mutableListOf()
        val listaMateria: MutableList<MutableList<Materia>> = mutableListOf()
        val listaIdCuatrimestre: MutableList<String> = mutableListOf()
        val listaNombreInformacion: MutableList<String> = mutableListOf()

        dataAlumnoList.filter {
            it.tipoInformacion.equals(TIPO_PAGO)
        }.forEach {
            var banderaRepetido: Boolean = true
            listaIdCuatrimestre.forEachIndexed { index, nombre ->
                if (nombre == it.idCuatrimestre) {
                    banderaRepetido = false
                }
            }

            if (banderaRepetido) {
                listaIdCuatrimestre.add(it.idCuatrimestre)
                listaNombreInformacion.add(it.nombreInformacion)
            }
        }

        listaIdCuatrimestre.forEachIndexed { index, id ->
            val listaEP: MutableList<EstatusPago> = mutableListOf()
            val listaM: MutableList<Materia> = mutableListOf()
            dataAlumnoList.forEachIndexed { indexDetalle, detalleAlumnoView ->
                if (id.toInt() == detalleAlumnoView.idCuatrimestre.toInt()) {
                    when (detalleAlumnoView.tipoInformacion) {
                        TIPO_PAGO -> listaEP.add(
                            EstatusPago(
                                detalleAlumnoView.pago,
                                detalleAlumnoView.concepto,
                                detalleAlumnoView.estatusPago
                            )
                        )
                        TIPO_PLAN -> listaM.add(
                            Materia(
                                detalleAlumnoView.idMateria!!,
                                detalleAlumnoView.materia!!,
                                detalleAlumnoView.estatusPlan!!
                            )
                        )
                    }
                }
            }
            listaEstatusPago.add(listaEP)
            listaMateria.add(listaM)
        }

        listaIdCuatrimestre.forEachIndexed { index, data ->
            pagos.add(Pagos(listaIdCuatrimestre[index], listaNombreInformacion[index], listaEstatusPago[index]))
            plan.add(Plan(listaIdCuatrimestre[index], listaNombreInformacion[index], listaMateria[index]))
        }
    }

    private fun inicializaActividad() {
        title = getString(R.string.title_fragment_pagos)
        pagosFragment = PagosFragment.initInstance(pagos)
        addFragment(pagosFragment!!)
        asignaturasFragment = AsignaturasFragment.initInstance(plan)
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
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.getItemId()) {
                R.id.menu_pagos_item -> {
                    selectedFragment = PagosFragment.newInstance()!!
                    title = getString(R.string.title_fragment_pagos)
                    filtroEdt_id.setVisibility(View.VISIBLE)
                }
                R.id.menu_asignaturas_item -> {
                    selectedFragment = AsignaturasFragment.newInstance()!!
                    title = getString(R.string.title_fragment_asignaturas)
                    filtroEdt_id.setVisibility(View.VISIBLE)
                }
                R.id.menu_estadisticas_item -> {
                    selectedFragment = EstadisticasFragment.newInstance(
                        pagos,
                        plan
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
        }

    }

    private fun inicializaMenuLateral() {
        txv_matricula_id.setText(listaDetalleAlumnoView[0].matricula)
        txv_nombre_completo_id.setText(listaDetalleAlumnoView[0].nombre + " " + listaDetalleAlumnoView[0].apellidoPaterno + " " + listaDetalleAlumnoView[0].apellidoMaterno)
        txv_fecha_nacimiento_id.text = parsearFechaCumpleanos(
            listaDetalleAlumnoView[0].fechaNacimiento!!,
            FORMATO_CUMPLEANOS
        )
        txv_curp_id.setText(listaDetalleAlumnoView[0].curp)
        txv_telefono_id.setText(listaDetalleAlumnoView[0].telefono)
        txv_carrera_id.setText(listaDetalleAlumnoView[0].licenciatura)
        txv_plantel_id.setText(listaDetalleAlumnoView[0].plantel)
        txv_cuatrimestre_id.setText(listaDetalleAlumnoView[0].cuatrimestre + " Cuatrimestre")

        obtieneImagenUsuario()
    }

    private fun obtieneImagenUsuario() {
        if (uriImagen != null) {
            picasso?.let { it.load(uriImagen).transform(PicassoCircleTransformation()).into(imv_estudiante_id) }
        } else if (listaDetalleAlumnoView[0].foto == null || listaDetalleAlumnoView[0].foto.equals("")) {
            picasso?.let {
                it.load(R.drawable.silueta_usuario).transform(PicassoCircleTransformation()).into(imv_estudiante_id)
            }
        } else {
            picasso?.let {
                it.load(listaDetalleAlumnoView[0].foto).transform(PicassoCircleTransformation())
                    .placeholder(R.drawable.silueta_usuario)
                    .error(R.drawable.silueta_usuario).into(imv_estudiante_id)
            }
        }
    }

    private fun alertaOpcional(bandera: Boolean, mensaje: String) {
        mensajeOpcional(this, mensaje)
            .setPositiveButton(R.string.action_accept) { dialog, which ->
                if (bandera) {
                    if (mainViewModel.getAllInformationResourceLiveData.hasObservers()) {
                        mainViewModel.getAllInformationResourceLiveData.removeObservers(this@MainActivity)
                        if (mainViewModel.deleteAlumno()) {
                            navigate<LoginActivity>()
                            finish()
                        }
                    }
                } else {
                    if (wifiManager.isWifiEnabled()) {
                        obtieneImagenUsuario()
                        mainViewModel.changePictureOnFromServer(archivoBase64(uriImagen?.let {
                            getFilePathFromContentUri(
                                it,
                                this@MainActivity
                            )
                        }))
                    } else {
                        Tools.informaErrorConexionWifi(
                            this@MainActivity,
                            getString(R.string.msg_no_conexion_internet),
                            false
                        )
                    }
                }
            }.show()
    }

    private fun seleccionaImagen() {
        selector(
            getString(R.string.msg_seleccione_opcion),
            resources.getStringArray(R.array.lista_opcion_foto).toList()
        ) { dialogInterface, i ->
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
        }
    }

    private fun limpiaFiltro() = filtroEdt_id.setText("")

    private fun comparteArchivo() {
        mainViewModel.downloadPdfOnFromServer(true)
    }
}