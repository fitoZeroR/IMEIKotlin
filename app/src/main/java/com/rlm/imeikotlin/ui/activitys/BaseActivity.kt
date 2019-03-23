package com.rlm.imeikotlin.ui.activitys

import android.animation.ValueAnimator
import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.activitys.login.LoginActivity
import com.rlm.imeikotlin.utils.Tools
import com.rlm.imeikotlin.utils.navigate
import com.rlm.imeikotlin.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mDrawerToggle: ActionBarDrawerToggle
    protected lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this !is LoginActivity) {
            setContentView(getLayoutResource())
            setSupportActionBar(toolbar)
            if (this !is MainActivity) {
                Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
                toolbar.setTitleTextColor(ContextCompat.getColor(this@BaseActivity, R.color.blanco))
            }
        }
    }

    override fun onBackPressed() = finish()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (this !is MainActivity)
            menuInflater.inflate(R.menu.enviar_informacion_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_editar_item -> {
                navigate<EnviarInformacionActivity>()
                return true
            }
        }
        return true
    }

    protected abstract fun getLayoutResource(): Int

    protected fun setActionBarIcon(iconRes: Int) = toolbar.setNavigationIcon(iconRes)

    protected fun cambiarTituloActionBar(titulo: String) = toolbar.setTitle(titulo)

    protected fun showLoading() {
        progressDialog = ProgressDialog.show(this@BaseActivity, "", getString(R.string.prb_cargando), true, false)
    }

    protected fun hideLoading() {
        progressDialog?.dismiss()
    }

    protected fun showError(mensaje: String?) = Tools.mensajeInformativo(this,
        mensaje ?: getString(R.string.error),
        false
    )

    // MainActivity
    protected fun confingToolBar() {
        mDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        animateToBackArrow()
        animatedToMenu()
    }

    protected fun animateToBackArrow() {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.addUpdateListener {
            val slideOffset = it.animatedValue as Float
            mDrawerToggle.onDrawerSlide(drawer_layout, slideOffset)
        }
        anim.interpolator = DecelerateInterpolator()
        // You can change this duration to more closely match that of the default animation.
        anim.duration = 500
        anim.start()
    }

    protected fun animatedToMenu() {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.addUpdateListener {
            mDrawerToggle.onDrawerClosed(drawer_layout)
        }
        anim.interpolator = DecelerateInterpolator()
        // You can change this duration to more closely match that of the default animation.
        anim.duration = 500
        anim.start()
    }

    protected fun addFragment(fragment: Fragment) = replaceFragment(fragment, R.id.frame_container)
}