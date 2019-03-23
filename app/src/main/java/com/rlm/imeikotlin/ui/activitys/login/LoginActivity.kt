package com.rlm.imeikotlin.ui.activitys.login

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.view.RxView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.modelo.Login
import com.rlm.imeikotlin.ui.activitys.BaseActivity
import com.rlm.imeikotlin.ui.activitys.opciones.OpcionesActivity
import com.rlm.imeikotlin.utils.Status
import com.rlm.imeikotlin.utils.Tools
import com.rlm.imeikotlin.utils.Tools.Companion.hideKeyboard
import com.rlm.imeikotlin.utils.Tools.Companion.mensajeInformativo
import com.rlm.imeikotlin.utils.navigate
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
import  androidx.lifecycle.Observer

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var loginResponse: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())

        requestForSpecificPermission()
        asignaEventosComponentes()
        subscribeToLoginModel()
    }

    override fun getLayoutResource() = R.layout.activity_login

    private fun subscribeToLoginModel() {
        loginViewModel.postCambiaPasswordResourceLiveData.observe(this, Observer {
            when(it.status) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    hideLoading()
                    mensajeInformativo(this, it.data!!.message, false)
                }
                Status.ERROR -> {
                    hideLoading()
                    showError(it.message)
                }
            }
        })
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
                        loginViewModel.changePasswordOnFromServer(matricula.let { it?.text.toString() })
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
                        /*loginPresenter.autentificaUsuario(edt_matricula_alumno_id.getText().toString()
                            , edt_password_id.getText().toString()
                        )*/
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