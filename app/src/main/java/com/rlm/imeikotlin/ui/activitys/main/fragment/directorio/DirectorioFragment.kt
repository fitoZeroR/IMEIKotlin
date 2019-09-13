package com.rlm.imeikotlin.ui.activity.main.fragment.directorio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.adapter.CustomAdapterDirectorio
import com.rlm.imeikotlin.utils.NAMED_LISTA_PLANTEL
import com.rlm.imeikotlin.utils.NAMED_LISTA_TELEFONICA
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*
import javax.inject.Inject
import dagger.android.support.AndroidSupportInjection
import javax.inject.Named

class DirectorioFragment : Fragment() {
    @Inject
    @field:Named(NAMED_LISTA_TELEFONICA)
    lateinit var listaDirectoriotelefonico: List<String>
    @Inject
    @field:Named(NAMED_LISTA_PLANTEL)
    lateinit var listaDirectorioPlantel: List<String>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv_principal_id.also {
            it.adapter = CustomAdapterDirectorio(listaDirectorioPlantel, listaDirectoriotelefonico) {
                startActivity(Intent(Intent.ACTION_CALL).setData(phoneNumberFormat(listaDirectoriotelefonico[it])))
            }
        }
    }

    private fun phoneNumberFormat(number: String) = Uri.parse("tel:$number")

    companion object {
        fun newInstance() = DirectorioFragment()
    }
}