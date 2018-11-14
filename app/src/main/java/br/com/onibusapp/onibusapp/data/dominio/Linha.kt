package br.com.onibusapp.onibusapp.data.dominio

import java.io.Serializable

/**
 * Created by diego on 15/07/2018.
 */

data class Linha (var id: Int,
             var numero: String,
             var codigoEmpresa: Int) : Serializable
