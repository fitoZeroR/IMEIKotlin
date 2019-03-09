package com.rlm.imeikotlin.ui.activity.enviarInformacion

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import com.jakewharton.rxbinding2.view.RxView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.presenter.EnviarInformacionPresenter
import com.rlm.imeikotlin.repository.modelo.EnviarInformacion
import com.rlm.imeikotlin.ui.activity.BaseActivity
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
    lateinit var enviarInformacionPresenter: EnviarInformacionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        enviarInformacionPresenter.setView(this)

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
                        enviarInformacionPresenter.enviaInformacion(edt_escribe_nombre_id.text.toString(), edt_escribe_telefono_id.text.toString(),
                            edt_escribe_correo_electronico_id.text.toString(), "vacio", edt_informacion_interes_id.text.toString())
                    } else
                        informaErrorConexionWifi(this@EnviarInformacionActivity, getString(R.string.msg_no_conexion_internet), false)
                }
            }
    }

    override fun onDestroy() {
        enviarInformacionPresenter.terminate()
        super.onDestroy()
    }

    override fun getLayoutResource() = R.layout.activity_enviar_informacion

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

    override fun respuestaEnvioInformacion(enviarInformacion: EnviarInformacion) {
        if (enviarInformacion.code === 0) {
            mensajeInformativo(this, getString(R.string.msg_datos_enviados_exitosamente), true)
        } else {
            mensajeInformativo(this, enviarInformacion.message, false)
        }
    }
}