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
import com.rlm.imeikotlin.repository.modelo.Plan
import com.rlm.imeikotlin.ui.adapter.CustomAdapterAsignaturas
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PLAN
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*

class AsignaturasFragment : Fragment() {
    private var plan: List<Plan>? = null
    private var customAdapterAsignaturas: CustomAdapterAsignaturas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            plan = Gson().fromJson(it!!.getString(BUNDLE_LISTA_PLAN), object : TypeToken<List<Plan>>() {}.type)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customAdapterAsignaturas = CustomAdapterAsignaturas(plan!!, context!!)
        // Llama Adaptador
        rcv_principal_id.layoutManager = LinearLayoutManager(context)
        rcv_principal_id.setHasFixedSize(true)
        rcv_principal_id.swapAdapter(customAdapterAsignaturas, true)
    }


    fun filtro(palabra: String) = customAdapterAsignaturas?.let {
        it.getFilter()?.filter(palabra)
    }

    companion object {
        var asignaturasFragment: AsignaturasFragment? = null

        fun initInstance(plan: List<Plan>): AsignaturasFragment {
            var asignaturasFragmentFun = AsignaturasFragment()

            val bundle = Bundle()
            bundle.putString(BUNDLE_LISTA_PLAN, Gson().toJson(plan))

            asignaturasFragmentFun.arguments = bundle

            asignaturasFragment = asignaturasFragmentFun
            return asignaturasFragmentFun
        }

        fun newInstance() = asignaturasFragment
    }
}