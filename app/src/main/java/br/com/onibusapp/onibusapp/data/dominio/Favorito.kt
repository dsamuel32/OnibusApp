package br.com.onibusapp.onibusapp.data.dominio

import java.io.Serializable

/**
 * Created by diego on 18/07/2018.
 */

class Favorito (var codigoLinha: Int?, var codigoSentido: Int?) : Serializable {

    var id: Int? = null
    var nomeLinha: String? = null
    var codigoEmpresa: Int? = null

    override fun toString(): String {
        return "Favorito {" +
                "id=" + id +
                ", codigoLinha=" + codigoLinha +
                ", codigoSentido=" + codigoSentido +
                ", nomeLinha=" + nomeLinha +
                ", codigoEmpresa=" + codigoEmpresa + "}"
    }
}
