package com.rlm.imeikotlin.ui.activitys.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.data.remote.model.response.Pagos
import com.rlm.imeikotlin.ui.adapters.CustomAdapterPagos
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PAGO
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*

class PagosFragment : Fragment() {
    private lateinit var pagos: List<Pagos>
    private lateinit var customAdapterPagos: CustomAdapterPagos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            pagos = Moshi.Builder().build().adapter<List<Pagos>>(Types.newParameterizedType(List::class.java, Pagos::class.java)).fromJson(it!!.getString(BUNDLE_LISTA_PAGO))!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customAdapterPagos = CustomAdapterPagos(pagos, context!!)
        rcv_principal_id.also {
            it.adapter = customAdapterPagos
        }
    }

    fun filtro(palabra: String) = customAdapterPagos.filter.filter(palabra)

    companion object {
        private var pagosFragment: PagosFragment? = null

        fun initInstance(pagos: List<Pagos>): PagosFragment {
            val pagosFragmentFun = PagosFragment()

            val bundle = Bundle()
            bundle.putString(BUNDLE_LISTA_PAGO, Moshi.Builder().build().adapter<List<Pagos>>(Types.newParameterizedType(List::class.java, Pagos::class.java)).toJson(pagos))
            pagosFragmentFun.arguments = bundle

            pagosFragment = pagosFragmentFun
            return pagosFragmentFun
        }

        fun newInstance() = pagosFragment
    }
}