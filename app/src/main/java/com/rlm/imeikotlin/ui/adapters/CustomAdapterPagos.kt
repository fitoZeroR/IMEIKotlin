package com.rlm.imeikotlin.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.model.response.Pagos
import com.rlm.imeikotlin.utils.inflate
import kotlinx.android.synthetic.main.row_asignaturas_pagos.view.*
import org.jetbrains.anko.dip

class CustomAdapterPagos() : RecyclerView.Adapter<CustomAdapterPagos.ViewHolder>(), Filterable {
    private lateinit var pagos: List<Pagos>
    private lateinit var context: Context
    private val pagosFiltro: MutableList<Pagos> = ArrayList()
    private lateinit var mFilter: CustomFilterPagos

    constructor(pagos: List<Pagos>, context: Context) : this() {
        this.pagos = pagos
        this.pagosFiltro.addAll(pagos)
        this.mFilter = CustomFilterPagos(this@CustomAdapterPagos)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(parent.inflate(R.layout.row_asignaturas_pagos))

    override fun getItemCount() = pagosFiltro.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position, pagosFiltro, context)

    override fun getFilter() = mFilter

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, pagosFiltro: MutableList<Pagos>, context: Context?) = with(itemView) {
            txv_titulo_cuatrimestre_id.text = pagosFiltro[position].nombre

            val linearLayoutVertical = LinearLayout(context)
            for (x in pagosFiltro[position].estatusPagos.indices) {
                val imvEstatusPago = ImageView(context)
                imvEstatusPago.id = (position + 1) * 1000
                val layoutParamsImagen = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParamsImagen.rightMargin = dip(5)
                layoutParamsImagen.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                layoutParamsImagen.addRule(RelativeLayout.CENTER_VERTICAL)
                imvEstatusPago.layoutParams = layoutParamsImagen
                when (pagosFiltro[position].estatusPagos[x].estatus) {
                    "Pagado" -> imvEstatusPago.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.alerta_exitoso
                        )
                    )
                    "Demorado" -> imvEstatusPago.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.alerta_peligro
                        )
                    )
                    "No Pagado" -> imvEstatusPago.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.alerta_error
                        )
                    )
                }

                val txvNombre = TextView(context)
                txvNombre.id = (position + 1) * 1001
                val layoutParamsMateria = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParamsMateria.topMargin = dip(5)
                layoutParamsMateria.addRule(RelativeLayout.RIGHT_OF, (position + 1) * 1000)
                txvNombre.layoutParams = layoutParamsMateria
                txvNombre.text = pagosFiltro[position].estatusPagos[x].nombre
                txvNombre.setTypeface(txvNombre.typeface, Typeface.BOLD)

                val txvEstatus = TextView(context)
                txvEstatus.id = (position + 1) * 1002
                val layoutParamsEstatus = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParamsEstatus.bottomMargin = dip(5)
                layoutParamsEstatus.addRule(RelativeLayout.RIGHT_OF, (position + 1) * 1000)
                layoutParamsEstatus.addRule(RelativeLayout.BELOW, (position + 1) * 1001)
                txvEstatus.layoutParams = layoutParamsEstatus
                txvEstatus.text = pagosFiltro[position].estatusPagos[x].estatus

                val relativeLayout = RelativeLayout(context)
                relativeLayout.layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                relativeLayout.addView(imvEstatusPago)
                relativeLayout.addView(txvNombre)
                relativeLayout.addView(txvEstatus)

                if (x < pagosFiltro[position].estatusPagos.size - 1) {
                    val separador = View(context)
                    val layoutParamsView = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip(1))
                    layoutParamsView.addRule(RelativeLayout.RIGHT_OF, (position + 1) * 1000)
                    layoutParamsView.addRule(RelativeLayout.BELOW, (position + 1) * 1002)
                    separador.layoutParams = layoutParamsView
                    separador.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
                    relativeLayout.addView(separador)
                }

                linearLayoutVertical.layoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                linearLayoutVertical.orientation = LinearLayout.VERTICAL
                linearLayoutVertical.addView(relativeLayout)
            }

            lly_contenido_id.removeAllViews()
            lly_contenido_id.addView(linearLayoutVertical)
        }
    }

    inner class CustomFilterPagos(private val customAdapterPagos: CustomAdapterPagos) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            pagosFiltro.clear()
            val results = FilterResults()
            if (constraint!!.isEmpty()) {
                pagosFiltro.addAll(pagos)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (p in pagos) {
                    if (p.nombre.toLowerCase().contains(filterPattern)) {
                        pagosFiltro.add(p)
                    }
                }
            }
            results.values = pagosFiltro
            results.count = pagosFiltro.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?)
                = this.customAdapterPagos.notifyDataSetChanged()
    }
}