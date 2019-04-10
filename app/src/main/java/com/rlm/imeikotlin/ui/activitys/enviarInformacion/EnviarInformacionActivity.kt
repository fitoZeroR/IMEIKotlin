package com.rlm.imeikotlin.ui.activitys.enviarInformacion

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import com.jakewharton.rxbinding2.view.RxView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.modelo.request.EnviarInformacionRequest
import com.rlm.imeikotlin.repository.remote.modelo.response.EnviarInformacionResponse
import com.rlm.imeikotlin.ui.activitys.BaseActivity
import com.rlm.imeikotlin.utils.Tools.Companion.hideKeyboard
import com.rlm.imeikotlin.utils.Tools.Companion.informaErrorConexionWifi
import com.rlm.imeikotlin.utils.Tools.Companion.mensajeInformativo
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_enviar_informacion.*
import org.jetbrains.anko.wifiManager
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EnviarInformacionActivity : BaseActivity() {
    @Inject
    lateinit var enviarInformacionViewModel: EnviarInformacionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        asignaEventosComponentes()
        subscribeToEnviarinformacionModel()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val iconMenuVisible = R.id.menu_editar_item
            if (item.itemId == iconMenuVisible) {
                item.isVisible = false
            }
        }
        return true
    }

    override fun getLayoutResource() = R.layout.activity_enviar_informacion

    private fun subscribeToEnviarinformacionModel() {
        enviarInformacionViewModel.postEnviarInformacionResourceLiveData.observe(this, androidx.lifecycle.Observer {
            administraObserverResources(it) {
                respuestaEnvioInformacion(it.data!!)
            }
        })
    }

    private fun asignaEventosComponentes() {
        RxView.clicks(btn_enviar_informacion_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hideKeyboard(this@EnviarInformacionActivity)
                if (TextUtils.isEmpty(edt_escribe_nombre_id.getText()) || TextUtils.isEmpty(edt_escribe_telefono_id.getText())
                    || TextUtils.isEmpty(edt_escribe_correo_electronico_id.getText()) || TextUtils.isEmpty(edt_informacion_interes_id.getText())) {
                    mensajeInformativo(
                        this@EnviarInformacionActivity,
                        getString(R.string.msg_no_campos_vacios),
                        false
                    )
                } else {
                    if (wifiManager.isWifiEnabled()) {
                        enviarInformacionViewModel.saveEnviarInformacionOnFromServer(
                            EnviarInformacionRequest(edt_escribe_nombre_id.text.toString(), edt_escribe_telefono_id.text.toString(),
                            edt_escribe_correo_electronico_id.text.toString(), "vacio", edt_informacion_interes_id.text.toString())
                        )
                    } else
                        informaErrorConexionWifi(this@EnviarInformacionActivity, getString(R.string.msg_no_conexion_internet), false)
                }
            }
    }

    fun respuestaEnvioInformacion(enviarInformacionResponse: EnviarInformacionResponse) {
        if (enviarInformacionResponse.code === 0) {
            mensajeInformativo(this, getString(R.string.msg_datos_enviados_exitosamente), true)
        } else {
            mensajeInformativo(this, enviarInformacionResponse.message, false)
        }
    }
}