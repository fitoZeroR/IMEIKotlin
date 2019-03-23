package com.rlm.imeikotlin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.remote.modelo.Grado
import com.rlm.imeikotlin.utils.EListado
import kotlinx.android.synthetic.main.row_opciones.view.*

class CustomAdapterOpciones(
    private val listaOpciones: List<String>?,
    private val gradoList: List<Grado>?,
    private val eListado: EListado,
    private val listener: (Int) -> Unit) : RecyclerView.Adapter<CustomAdapterOpciones.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_opciones, parent, false)
        val opcionesViewHolder = ViewHolder(itemView)

        opcionesViewHolder.create(eListado)

        return opcionesViewHolder
    }

    override fun getItemCount(): Int = if (eListado === EListado.OPCION || eListado === EListado.SELECCIONA_OPCION_DIPLOMADO)
        listaOpciones?.size!!
    else
        gradoList?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position, eListado,
        listaOpciones?.get(position),
        gradoList?.get(position), listener)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun create(eListado: EListado) = with(itemView) {
            when (eListado) {
                EListado.SELECCIONA_OPCION -> {
                    rll_selecciona_opcion_id.setVisibility(View.VISIBLE)
                    rll_opcion_id.setVisibility(View.GONE)
                    rll_selecciona_opcion_titulo_id.setVisibility(View.GONE)
                    rll_opcion_sin_flecha_id.setVisibility(View.GONE)
                }
                EListado.SELECCIONA_OPCION_SIN_PLANTEL -> {
                    rll_selecciona_opcion_id.setVisibility(View.GONE)
                    rll_opcion_id.setVisibility(View.GONE)
                    rll_selecciona_opcion_titulo_id.setVisibility(View.VISIBLE)
                    rll_opcion_sin_flecha_id.setVisibility(View.GONE)
                }
                EListado.SELECCIONA_OPCION_DIPLOMADO -> {
                    rll_selecciona_opcion_id.setVisibility(View.GONE)
                    rll_opcion_id.setVisibility(View.GONE)
                    rll_selecciona_opcion_titulo_id.setVisibility(View.GONE)
                    rll_opcion_sin_flecha_id.setVisibility(View.VISIBLE)
                }
            }
        }

        fun bind(position: Int, eListado: EListado, opcionLista: String?, grado: Grado?, listener: (Int) -> Unit)= with(itemView) {
            when (eListado) {
                EListado.SELECCIONA_OPCION -> {
                    txv_titulo_id.text = grado?.titulo
                    txv_planteles_id.setText(grado?.planteles)
                }
                EListado.SELECCIONA_OPCION_SIN_PLANTEL -> txv_titulo_sin_plantel_id.text = grado?.titulo
                EListado.SELECCIONA_OPCION_DIPLOMADO -> texto_opciones_sin_flecha_id.setText(opcionLista)
                else -> texto_opciones_id.setText(opcionLista)
            }

            setOnClickListener { listener(position) }
        }
    }
}