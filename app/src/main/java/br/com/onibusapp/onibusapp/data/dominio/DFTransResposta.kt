package br.com.onibusapp.onibusapp.data.dominio

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by diego on 12/07/2018.
 */

data class DFTransResposta(
            @SerializedName("Campos")
            var campos: MutableList<String> = arrayListOf(),
            @SerializedName("Dados")
            var dados: MutableList<List<String>> = arrayListOf()
        ) : Serializable
