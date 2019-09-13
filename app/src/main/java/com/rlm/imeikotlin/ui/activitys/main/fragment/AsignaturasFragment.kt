package com.rlm.imeikotlin.ui.activity.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.model.response.Plan
import com.rlm.imeikotlin.ui.adapter.CustomAdapterAsignaturas
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PLAN
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.fragment_menu_bottom_navigation_view.*

class AsignaturasFragment : Fragment() {
    private lateinit var plan: List<Plan>
    private lateinit var customAdapterAsignaturas: CustomAdapterAsignaturas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            plan = Moshi.Builder().build().adapter<List<Plan>>(Types.newParameterizedType(List::class.java, Plan::class.java)).fromJson(it!!.getString(BUNDLE_LISTA_PLAN))!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_menu_bottom_navigation_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customAdapterAsignaturas = CustomAdapterAsignaturas(plan, context!!)
        rcv_principal_id.also {
            it.adapter = customAdapterAsignaturas
        }
    }


    fun filtro(palabra: String) = customAdapterAsignaturas.let {
        it.filter?.filter(palabra)
    }

    companion object {
        private var asignaturasFragment: AsignaturasFragment? = null

        fun initInstance(plan: List<Plan>): AsignaturasFragment {
            val asignaturasFragmentFun = AsignaturasFragment()

            val bundle = Bundle()
            bundle.putString(BUNDLE_LISTA_PLAN, Moshi.Builder().build().adapter<List<Plan>>(Types.newParameterizedType(List::class.java, Plan::class.java)).toJson(plan))

            asignaturasFragmentFun.arguments = bundle

            asignaturasFragment = asignaturasFragmentFun
            return asignaturasFragmentFun
        }

        fun newInstance() = asignaturasFragment
    }
}