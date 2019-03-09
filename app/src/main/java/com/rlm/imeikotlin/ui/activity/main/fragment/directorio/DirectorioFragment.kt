package com.rlm.imeikotlin.ui.activity.main.fragment.directorio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.adapter.CustomAdapterDirectorio
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*
import javax.inject.Inject
import com.rlm.imeikotlin.utils.showToast
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

//class DirectorioFragment : Fragment() {
class DirectorioFragment : DaggerFragment() {
    @Inject
    lateinit var nombreAutor: String

    /*override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.showToast(nombreAutor)

        val arrayDirectorioTelefnico = resources.getStringArray(R.array.lista_telefono_directorio)

        rcv_principal_id.layoutManager = LinearLayoutManager(context)
        rcv_principal_id.setHasFixedSize(true)
        rcv_principal_id.swapAdapter(CustomAdapterDirectorio(resources.getStringArray(R.array.lista_plantel_directorio).toList(),
            arrayDirectorioTelefnico.toList()) {
            startActivity(Intent(Intent.ACTION_CALL).setData(phoneNumberFormat(arrayDirectorioTelefnico[it])))
        }, true)
    }

    private fun phoneNumberFormat(number: String) = Uri.parse("tel:$number")

    companion object {
        fun newInstance() = DirectorioFragment()
    }
}