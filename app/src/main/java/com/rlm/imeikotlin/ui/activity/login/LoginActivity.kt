package com.rlm.imeikotlin.ui.activity.login

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.presenter.LoginPresenter
import com.rlm.imeikotlin.repository.modelo.Login
import com.rlm.imeikotlin.repository.modelo.PagosAsignaturas
import com.rlm.imeikotlin.repository.modelo.RecuperarPassword
import com.rlm.imeikotlin.ui.activity.BaseActivity
import com.rlm.imeikotlin.ui.activity.main.MainActivity
import com.rlm.imeikotlin.ui.activity.opciones.OpcionesActivity
import com.rlm.imeikotlin.utils.*
import com.rlm.imeikotlin.utils.Tools.Companion.hideKeyboard
import com.rlm.imeikotlin.utils.Tools.Companion.mensajeInformativo
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var loginPresenter: LoginPresenter
    @Inject
    lateinit var preferencias: SharedPreferences

    private lateinit var loginResponse: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        loginPresenter.setView(this)

        if ((preferencias.getString(MATRICULA, "") == "") and (preferencias.getString(PASSWORD, "") == "")) {
            setContentView(getLayoutResource())

            requestForSpecificPermission()
            asignaEventosComponentes()
        } else {
            inicializaActividadPrincipal(
                preferencias.getString(JSON_LOGIN, ""),
                preferencias.getString(JSON_ASIGNATURAS_PAGOS, "")
            )
        }
    }

    override fun onDestroy() {
        loginPresenter.terminate()
        super.onDestroy()
    }

    override fun getLayoutResource() = R.layout.activity_login

    override fun respuestaLogin(login: Login) {
        if (login.message.equals(getString(R.string.msg_operacion_exitosa))) {
            loginResponse = login
            loginPresenter.consultaListaAsignaturasPagos(login.data.tokenSesion)
        } else {
            hideLoading()
            mensajeInformativo(this, getString(R.string.msg_usuario_no_valido), false)
        }
    }

    override fun despliegaPagosAsignaturas(pagosAsignaturas: PagosAsignaturas) {
        val editorPreferencias = preferencias.edit()
        editorPreferencias.putString(MATRICULA_SP, edt_matricula_alumno_id.text.toString())
        editorPreferencias.putString(PASSWORD, edt_password_id.text.toString())
        editorPreferencias.putString(JSON_LOGIN, Gson().toJson(loginResponse))
        editorPreferencias.putString(JSON_ASIGNATURAS_PAGOS, Gson().toJson(pagosAsignaturas))
        editorPreferencias.commit()

        inicializaActividadPrincipal(Gson().toJson(loginResponse), Gson().toJson(pagosAsignaturas))
    }

    override fun muestraRespuestaRecuperarPassword(recuperarPassword: RecuperarPassword) {
        if (recuperarPassword.code === 0) {
            mensajeInformativo(this, recuperarPassword.message, false)
        } else {
            mensajeInformativo(this, recuperarPassword.trace, false)
        }
    }

    private fun inicializaActividadPrincipal(responseLogin: String?, responsePagosAsignaturas: String?) {
        startActivity<MainActivity>(
            BUNDLE_INFORMACION_USUARIO to responseLogin,
            BUNDLE_INFORMACION_PAGOS_ASIGNATURAS to responsePagosAsignaturas)
        finish()
    }

    private fun disparaDialogRecuperarPassword() {
        // DSL
        var matricula: TextInputEditText? = null
        val dialogoRecuperaPassword = alert(getString(R.string.msg_dialog_mensaje) + " " + getString(R.string.hint_dialog_mensaje).toLowerCase(), getString(R.string.msg_dialog_titulo)) {
            customView {
                verticalLayout {
                    textInputLayout {
                        hint = getString(R.string.hint_dialog_mensaje)
                        matricula = textInputEditText{
                            textSize = 16f
                        }
                    }
                }
            }
            positiveButton(getString(R.string.action_enviar)) {
                it.dismiss()
                hideKeyboard(this@LoginActivity)
                if (matricula?.text!!.isEmpty()) {
                    mensajeInformativo(this@LoginActivity,
                        getString(R.string.msg_matricula_vacio),
                        false)
                } else {
                    if (wifiManager.isWifiEnabled()) {
                        loginPresenter.recuperaPassword(matricula?.text.toString())
                    } else
                        Tools.informaErrorConexionWifi(
                            this@LoginActivity,
                            getString(R.string.msg_no_conexion_internet),
                            false
                        )
                }
            }
            negativeButton(getString(R.string.action_cancel)) {
                it.dismiss()
            }
        }.build()
        dialogoRecuperaPassword.setCancelable(false)
        dialogoRecuperaPassword.setCanceledOnTouchOutside(false)
        dialogoRecuperaPassword.show()

        matricula?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                Objects.requireNonNull(dialogoRecuperaPassword.window).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        // val alert = with(activity) { alert { customView = vertivalLayout {} } } alert.show()

        /*alert {
            layoutInflater.inflate(R.layout.activity_enviar_informacion, null)
        }.show()*/

        /*alert("Hi, I'm Rashi", "Do you find my posts helpful?") {
            yesButton { toast("Thanks, will start writing more :)") }
            noButton {
                toast("Alright, let's start working on improvements") }
        }.show()*/

        /*alert {
            title = "Hi, Its Rashi again"
            positiveButton("Cool") { toast("Yay :D") }
            negativeButton("Cancelar") {}
            customView {
                verticalLayout {
                    textView("Follow me for posts on tech, design and spirituality.")
                    button{
                        text = "I'm in!"
                        setOnClickListener { toast("Yay :eddeD") }
                    }
                    padding = dip(16)
                }
            }
        }.show()*/
    }

    private fun asignaEventosComponentes() {
        RxView.clicks(btn_entrar_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aVoid ->
                hideKeyboard(this)
                if (TextUtils.isEmpty(edt_matricula_alumno_id.text) || TextUtils.isEmpty(edt_password_id.getText()))
                    mensajeInformativo(
                        this@LoginActivity,
                        getString(R.string.msg_no_campos_vacios),
                        false
                    )
                else
                    if (wifiManager.isWifiEnabled())
                        loginPresenter.autentificaUsuario(edt_matricula_alumno_id.getText().toString()
                            , edt_password_id.getText().toString()
                        )
                    else
                        Tools.informaErrorConexionWifi(
                            this@LoginActivity,
                            getString(R.string.msg_no_conexion_internet),
                            false
                        )
            }

        RxView.clicks(txv_presiona_aqui_no_alumno_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aVoid ->
                hideKeyboard(this)
                if (wifiManager.isWifiEnabled())
                    navigate<OpcionesActivity>()
                else
                    Tools.informaErrorConexionWifi(
                        this@LoginActivity,
                        getString(R.string.msg_no_conexion_internet),
                        false
                    )
            }

        /*txv_presiona_aqui_no_alumno_id.setOnClickListener {
            if (isConnectionNetwork(this))
                navigate<OpcionesActivity>()
            else
                mensajeInformativo(
                    this@LoginActivity,
                    getString(R.string.msg_no_conexion_internet),
                    false
                )
        }*/

        RxView.clicks(txv_olvidar_password_id)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aVoid ->
                hideKeyboard(this)
                disparaDialogRecuperarPassword()
            }
    }

    private fun requestForSpecificPermission() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.CALL_PHONE
        )
            .subscribe { granted -> }
    }
}