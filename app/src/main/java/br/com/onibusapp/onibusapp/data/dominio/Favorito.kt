package br.com.onibusapp.onibusapp.data.dominio

import java.io.Serializable

/**
 * Created by diego on 18/07/2018.
 */

class Favorito (var id: Int? = null,
                var codigoSentido: Int,
                var linha: String,
                var url: String
                ) : Serializable {

    override fun toString(): String {
        return "Favorito {" +
                "id=" + id +
                ", codigoSentido=" + codigoSentido +
                ", linha=" + linha +
                ", url=" + url + "}"
    }
}
