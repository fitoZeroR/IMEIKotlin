package com.rlm.imeikotlin.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.utils.inflate
import kotlinx.android.synthetic.main.row_directorio.view.*

class CustomAdapterDirectorio(
    private val listaPlanteles: List<String>,
    private val listaTelefonos: List<String>,
    private val listener: (Int) -> Unit) : RecyclerView.Adapter<CustomAdapterDirectorio.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.row_directorio))

    override fun getItemCount() = listaPlanteles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position, listaPlanteles[position],
        listaTelefonos[position], listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, plantel: String, telefono: String, listener: (Int) -> Unit) = with(itemView) {

            txv_plantel_id.setText(plantel)
            txv_telefono_id.setText(telefono)

            setOnClickListener { listener(position) }
        }
    }
}