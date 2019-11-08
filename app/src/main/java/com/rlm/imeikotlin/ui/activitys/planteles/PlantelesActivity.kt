package com.rlm.imeikotlin.ui.activitys.planteles

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.data.local.entity.PlantelEntity
import com.rlm.imeikotlin.ui.activitys.BaseActivity
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import com.rlm.imeikotlin.utils.Tools
import dagger.android.AndroidInjection
import org.jetbrains.anko.wifiManager
import java.util.*
import javax.inject.Inject

class PlantelesActivity : BaseActivity(), OnMapReadyCallback {
    @Inject
    lateinit var plantelesViewModel: PlantelesViewModel

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        intent.extras.let {
            title = it.getString(BUNDLE_NOMBRE_OPCION)
        }

        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        if (wifiManager.isWifiEnabled) {
            subscribeToPlantelesModel()
            plantelesViewModel.loadAllPlanteles(true)
        } else
            Tools.informaErrorConexionWifi(
                this@PlantelesActivity,
                getString(R.string.msg_no_conexion_internet),
                false
            )
    }

    override fun getLayoutResource() = R.layout.activity_planteles


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        this.googleMap?.isMyLocationEnabled = true
    }

    private fun subscribeToPlantelesModel() {
        plantelesViewModel.getAllOPlantelesResourceLiveData.observe(this, Observer {
            administraObserverResources(it) {
                despliegaPlanteles(it.data)
            }
        })
    }

    private fun despliegaPlanteles(listaPlanteles: List<PlantelEntity>?) {
        for (x in listaPlanteles!!.indices) {
            this.googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(listaPlanteles[x].latitud.toDouble(), listaPlanteles[x].longitud.toDouble()))
                    .title(listaPlanteles[x].nombre)
            )

            if (x == listaPlanteles.size - 1) {
                val zoomCamara = CameraUpdateFactory.newLatLngZoom(
                    LatLng(listaPlanteles[x].latitud.toDouble(), listaPlanteles[x].longitud.toDouble()), 10f)
                this.googleMap?.moveCamera(zoomCamara)
            }
        }

        this.googleMap?.setOnMarkerClickListener { marker ->
            for (x in listaPlanteles.indices) {
                if (marker.title == listaPlanteles[x].nombre) {
                    val uri = Uri.parse(
                        "geo:" + listaPlanteles[x].latitud + "," + listaPlanteles[x].longitud + "?q="
                                + listaPlanteles[x].latitud + "," + listaPlanteles[x].longitud
                                + "(" + listaPlanteles[x].nombre + ")"
                    )
                    val intentMapas = Intent(Intent.ACTION_VIEW, uri)

                    // Crea e inicia el diálogo de selección
                    val chooser = Intent.createChooser(intentMapas, resources.getText(R.string.msg_seleccione_app))
                    startActivity(chooser)
                    break
                }

            }
            true
        }
    }
}