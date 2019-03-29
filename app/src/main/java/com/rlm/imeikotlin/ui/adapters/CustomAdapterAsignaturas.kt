package com.rlm.imeikotlin.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.modelo.response.Plan
import com.rlm.imeikotlin.utils.inflate
import kotlinx.android.synthetic.main.row_asignaturas_pagos.view.*
import org.jetbrains.anko.dip
import java.util.ArrayList

class CustomAdapterAsignaturas() : RecyclerView.Adapter<CustomAdapterAsignaturas.ViewHolder>(), Filterable {
    private var plan: List<Plan>? = null
    private var planFiltro: MutableList<Plan> = ArrayList()
    private var mFilter: CustomFilterAsignaturas? = null
    private var context: Context? = null

    constructor (plan: List<Plan>, context: Context) : this() {
        this.plan = plan
        this.planFiltro.addAll(plan)
        this.mFilter = CustomFilterAsignaturas(this@CustomAdapterAsignaturas)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(parent.inflate(R.layout.row_asignaturas_pagos))

    override fun getItemCount() = planFiltro.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position, planFiltro, context)

    override fun getFilter() = mFilter

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, planFiltro: MutableList<Plan>, context: Context?) = with(itemView) {
            txv_titulo_cuatrimestre_id.text = planFiltro[position].nombre

            val linearLayout = LinearLayout(context)
            for (x in 0 until planFiltro[position].materia.size) {
                linearLayout.layoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                linearLayout.orientation = LinearLayout.VERTICAL

                val txvMateria = TextView(context)
                val layoutParamsMateria =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParamsMateria.topMargin = dip(5)
                txvMateria.layoutParams = layoutParamsMateria
                txvMateria.setText(planFiltro[position].materia[x].materia)
                txvMateria.setTypeface(txvMateria.typeface, Typeface.BOLD)
                txvMateria.id = position
                linearLayout.addView(txvMateria)

                val txvEstatus = TextView(context)
                val layoutParamsEstatus =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParamsEstatus.bottomMargin = dip(5)
                txvEstatus.layoutParams = layoutParamsEstatus
                txvEstatus.setText(planFiltro[position].materia[x].estatus)
                txvEstatus.id = position
                linearLayout.addView(txvEstatus)

                if (x < planFiltro[position].materia.size - 1) {
                    val separador = View(context)
                    separador.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip(1))
                    separador.id = position
                    separador.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
                    linearLayout.addView(separador)
                }
            }

            lly_contenido_id.removeAllViews()
            lly_contenido_id.addView(linearLayout);
        }
    }

    inner class CustomFilterAsignaturas(private val customAdapterAsignaturas: CustomAdapterAsignaturas) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            planFiltro.clear()
            val results = Filter.FilterResults()
            if (constraint!!.length == 0) {
                planFiltro.addAll(plan!!)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (p in plan!!) {
                    if (p.nombre.toLowerCase().contains(filterPattern)) {
                        planFiltro.add(p)
                    }
                }
            }
            results.values = planFiltro
            results.count = planFiltro!!.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?)
                = this.customAdapterAsignaturas.notifyDataSetChanged()
    }
}