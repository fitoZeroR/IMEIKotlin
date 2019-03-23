package com.rlm.imeikotlin.ui.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.utils.BUNDLE_DESCRIPCION
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import kotlinx.android.synthetic.main.activity_descripcion.*
import java.util.*

class DescripcionActivity : BaseActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        val bundle = intent.extras
        var descripcion: String? = null
        if (bundle != null) {
            descripcion = bundle.getString(BUNDLE_DESCRIPCION)
            title = bundle.getString(BUNDLE_NOMBRE_OPCION)
        }

        txv_descripcion_id.setText(Html.fromHtml(descripcion, 0), TextView.BufferType.SPANNABLE)
    }

    override fun getLayoutResource() = R.layout.activity_descripcion
}