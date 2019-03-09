package com.rlm.imeikotlin.ui.activity.plantel

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.presenter.PlantelPresenter
import com.rlm.imeikotlin.repository.modelo.InformacionPlanteles
import com.rlm.imeikotlin.ui.activity.BaseActivity
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import com.rlm.imeikotlin.utils.Tools
import dagger.android.AndroidInjection
import org.jetbrains.anko.wifiManager
import java.lang.Double
import java.util.*
import javax.inject.Inject

class PlantelesActivity : BaseActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Inject
    lateinit var plantelPresenter: PlantelPresenter

    private var googleMap: GoogleMap? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        plantelPresenter.setView(this)

        val bundleExtras = intent.extras
        if (bundleExtras != null) {
            title = bundleExtras.getString(BUNDLE_NOMBRE_OPCION)
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        }

        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        if (wifiManager.isWifiEnabled()) {
            plantelPresenter.consultaListaPlanteles()
        } else
            Tools.informaErrorConexionWifi(
                this@PlantelesActivity,
                getString(R.string.msg_no_conexion_internet),
                false
            )
    }

    override fun onStart() {
        mGoogleApiClient?.connect()
        super.onStart()
    }

    override fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onDestroy() {
        plantelPresenter.terminate()
        super.onDestroy()
    }

    override fun getLayoutResource() = R.layout.activity_planteles

    override fun despliegaPlanteles(informacionPlanteles: InformacionPlanteles) {
        for (x in 0 until informacionPlanteles.planteles.size) {
            this.googleMap?.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            Double.parseDouble(informacionPlanteles.planteles[x].latitud),
                            Double.parseDouble(informacionPlanteles.planteles.get(x).longitud)
                        )
                    )
                    .title(informacionPlanteles.planteles[x].nombre)
            )

            if (x == informacionPlanteles.planteles.size - 1) {
                val zoomCamara = CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        Double.parseDouble(informacionPlanteles.planteles[x].latitud),
                        Double.parseDouble(informacionPlanteles.planteles.get(x).longitud)
                    ), 10f
                )
                this.googleMap?.moveCamera(zoomCamara)
            }
        }

        this.googleMap?.setOnMarkerClickListener { marker ->
            for (x in 0 until informacionPlanteles.planteles.size) {
                if (marker.title == informacionPlanteles.planteles.get(x).nombre) {
                    val uri = Uri.parse(
                        "geo:" + informacionPlanteles.planteles.get(x).latitud + "," + informacionPlanteles.planteles.get(x)
                            .longitud + "?q=" + informacionPlanteles.planteles[x].latitud + "," + informacionPlanteles.planteles[x].longitud
                                + "(" + informacionPlanteles.planteles[x].nombre + ")"
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        this.googleMap?.setMyLocationEnabled(true)
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}