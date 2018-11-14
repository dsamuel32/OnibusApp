package br.com.onibusapp.onibusapp.data.dominio

import java.io.Serializable

/**
 * Created by diego on 17/07/2018.
 */

data class Filtro(var linha: String,
                  var sentido: Int,
                  var adicionarFavoritos: Boolean,
                  var codigoEmprea: Int) : Serializable {

    override fun toString(): String {
        return "Filtro{" +
                "linha='" + linha + '\''.toString() +
                ", sentido=" + sentido +
                ", adicionarFavoritos=" + adicionarFavoritos +
                ", codigoEmpresa=" + codigoEmprea +
                '}'.toString()
    }
}
