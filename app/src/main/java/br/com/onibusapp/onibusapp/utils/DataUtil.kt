package br.com.onibusapp.onibusapp.utils

import java.util.*

class DataUtil {

    private val SEGUNDA_SEXTA = "Seg-Sex"
    private val SABADO = "Sábado"
    private val DOMINGO = "Domingo"

    var mapaDiasSemana: MutableMap<Int, String> = mutableMapOf()

    init {
        this.mapaDiasSemana.put(0, DOMINGO)
        this.mapaDiasSemana.put(1, SEGUNDA_SEXTA)
        this.mapaDiasSemana.put(2, SEGUNDA_SEXTA)
        this.mapaDiasSemana.put(3, SEGUNDA_SEXTA)
        this.mapaDiasSemana.put(4, SEGUNDA_SEXTA)
        this.mapaDiasSemana.put(5, SEGUNDA_SEXTA)
        this.mapaDiasSemana.put(6, SABADO)
    }

    fun recuperarDiaSemana(): String {
        val calendar: Calendar = Calendar.getInstance()
        val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        return this.mapaDiasSemana.get(diaSemana)!!
    }
}