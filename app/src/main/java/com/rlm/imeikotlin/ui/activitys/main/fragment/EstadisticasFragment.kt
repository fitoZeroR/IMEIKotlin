package com.rlm.imeikotlin.ui.activity.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PAGO
import com.rlm.imeikotlin.utils.BUNDLE_LISTA_PLAN
import com.rlm.imeikotlin.repository.remote.model.response.Pagos
import com.rlm.imeikotlin.repository.remote.model.response.Plan
import com.rlm.imeikotlin.utils.withArgs
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_estadisticas.*
import java.util.ArrayList

class EstadisticasFragment : Fragment() {
    private lateinit var pagos: List<Pagos>
    private lateinit var plan: List<Plan>

    private var aprobada = 0
    private var cursada: Int = 0
    private var noCursada: Int = 0
    private var pagado: Int = 0
    private var noPagado: Int = 0
    private var demorado: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            pagos = Moshi.Builder().build().adapter<List<Pagos>>(Pagos::class.java).fromJson(it!!.getString(BUNDLE_LISTA_PAGO))!!
            plan = Moshi.Builder().build().adapter<List<Plan>>(Plan::class.java).fromJson(it!!.getString(BUNDLE_LISTA_PLAN))!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_estadisticas, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BarChart
        obtieneStatusPago()
        val barDataSet = BarDataSet(addValuesToBarEntryPagos(), "Etiqueta")
        val barData = BarData(addValuesToBarEntryLabelsPagos(), barDataSet)
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        bar_chart_id.setData(barData)
        bar_chart_id.setDescription(getString(R.string.title_fragment_pagos))
        bar_chart_id.animateY(3000)

        // PieChart
        obtieneStatusAsignaturas()
        val pieDataSet = PieDataSet(addValuesToPieEntryAsignaturas(), "")
        val pieData = PieData(addValuesToEntryLabelsAsignaturas(), pieDataSet)
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS)
        pie_chart_id.setData(pieData)
        pie_chart_id.setDescription(getString(R.string.title_fragment_asignaturas))
        pie_chart_id.animateY(3000)

        /*demorado = 0
        noPagado = demorado
        pagado = noPagado
        noCursada = pagado
        cursada = noCursada
        aprobada = cursada*/
    }

    private fun addValuesToBarEntryPagos(): List<BarEntry> {
        val barEntryList = ArrayList<BarEntry>()
        with(barEntryList) {
            add(BarEntry(pagado.toFloat(), 0))
            add(BarEntry(noPagado.toFloat(), 1))
            add(BarEntry(demorado.toFloat(), 2))
        }
        return barEntryList
    }

    private fun addValuesToBarEntryLabelsPagos(): List<String> {
        val entryLabels = ArrayList<String>()
        with(entryLabels) {
            add("Pagado")
            add("No Pagado")
            add("Demorado")
        }
        return entryLabels
    }

    private fun obtieneStatusPago() {
        pagos.forEachIndexed { indexPago, pago ->
            pagos[indexPago].estatusPagos.forEachIndexed { indexEstatus, estatusPago ->
                when (pagos!![indexPago].estatusPagos[indexEstatus].estatus) {
                    "Pagado" -> pagado++
                    "No Pagado" -> noPagado++
                    else -> demorado++
                }
            }
        }

        /*for (x in pagos!!.indices) {
            for (y in 0 until pagos!![x].estatusPagos.size)
                when (pagos!![x].estatusPagos[y].estatus) {
                    "Pagado" -> pagado++
                    "No Pagado" -> noPagado++
                    else -> demorado++
                }
        }*/
    }

    private fun addValuesToPieEntryAsignaturas(): List<Entry> {
        val entries = ArrayList<Entry>()
        with(entries) {
            add(BarEntry(aprobada.toFloat(), 0))
            add(BarEntry(cursada.toFloat(), 1))
            add(BarEntry(noCursada.toFloat(), 2))
        }
        return entries
    }

    private fun addValuesToEntryLabelsAsignaturas(): List<String> {
        val entryLabels = ArrayList<String>()
        with(entryLabels) {
            add("Aprobada")
            add("Cursada")
            add("No Cursada")
        }
        return entryLabels
    }

    private fun obtieneStatusAsignaturas() {
        plan.forEachIndexed { indexPlan, p ->
            plan[indexPlan].materia.forEachIndexed { indexMateria, materia ->
                when (plan!![indexPlan].materia[indexMateria].estatus) {
                    "Aprobada" -> aprobada++
                    "Cursada" -> cursada++
                    else -> noCursada++
                }
            }
        }

        /*for (x in plan!!.indices) {
            for (y in 0 until plan!![x].materia.size)
                when (plan!![x].materia[y].estatus) {
                    "Aprobada" -> aprobada++
                    "Cursada" -> cursada++
                    else -> noCursada++
                }
        }*/
    }

    companion object {
        fun newInstance(pagos: List<Pagos>, plan: List<Plan>) = EstadisticasFragment().withArgs {
            putString(BUNDLE_LISTA_PAGO, Moshi.Builder().build().adapter<List<Pagos>>(Pagos::class.java).toJson(pagos))
            putString(BUNDLE_LISTA_PLAN, Moshi.Builder().build().adapter<List<Plan>>(Plan::class.java).toJson(plan))
        }
    }
}