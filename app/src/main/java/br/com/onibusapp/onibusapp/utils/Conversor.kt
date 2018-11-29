package br.com.onibusapp.onibusapp.utils

import br.com.onibusapp.onibusapp.data.dominio.DFTransResposta
import br.com.onibusapp.onibusapp.data.dominio.Onibus
import com.google.gson.Gson

/**
 * Created by diego on 06/08/2018.
 */

class Conversor {

    private var gson: Gson
    private lateinit var resposta: String

    init {
        this.gson = Gson()
    }

    fun from(resposta: String): Conversor {
        this.resposta = resposta
        return this
    }

    fun toListOnibus(): MutableList<Onibus> {
        val dfTransResposta = this.gson.fromJson(this.resposta, DFTransResposta::class.java)
        val listaOnibus: MutableList<Onibus> = arrayListOf()
        val stringUtils: StringUtils = StringUtils()
        for (dados in dfTransResposta.dados) {
            if (dados[5] != null && this.isExisteLocalizacao(dados[2], dados[3])) {
                val onibus = Onibus(dados[0],
                                    dados[1],
                                    stringUtils.toDouble(dados[2]),
                                    stringUtils.toDouble(dados[3]),
                            0,
                                    dados[5],
                                    dados[6],
                                    dados[7])
                listaOnibus.add(onibus)
            }

        }

         return listaOnibus
    }

    fun isExisteLocalizacao(lat: String, lon: String): Boolean {
        return isPreenchido(lat) && isPreenchido(lon)
    }

    fun isPreenchido(localizacao: String): Boolean {
        return localizacao != null && localizacao.isNotEmpty()
    }

}
