package com.rlm.imeikotlin.ui.activity.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.modelo.Pagos
import com.rlm.imeikotlin.ui.adapter.CustomAdapterPagos
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PAGO
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*

class PagosFragment : Fragment() {
    private var pagos: List<Pagos>? = null
    private var customAdapterPagos: CustomAdapterPagos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            pagos = Gson().fromJson(it!!.getString(BUNDLE_LISTA_PAGO), object : TypeToken<List<Pagos>>() {}.type)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customAdapterPagos = CustomAdapterPagos(pagos!!, context!!)
        // Llama Adaptador
        rcv_principal_id.layoutManager = LinearLayoutManager(context)
        rcv_principal_id.setHasFixedSize(true)
        rcv_principal_id.swapAdapter(customAdapterPagos, true)
    }

    fun filtro(palabra: String) = customAdapterPagos?.let {
        it.getFilter()?.filter(palabra)
    }

    companion object {
        var pagosFragment: PagosFragment? = null

        fun initInstance(pagos: List<Pagos>): PagosFragment {
            var pagosFragmentFun = PagosFragment()

            val bundle = Bundle()
            bundle.putString(BUNDLE_LISTA_PAGO, Gson().toJson(pagos))
            pagosFragmentFun.arguments = bundle

            pagosFragment = pagosFragmentFun
            return pagosFragmentFun
        }

        fun newInstance() = pagosFragment
    }
}