package br.com.onibusapp.onibusapp.utils

import android.content.Context
import java.lang.NumberFormatException

/**
 * Created by diego on 26/07/2018.
 */

class StringUtils {

    fun getStringValues(string: Int, context: Context): String = context.getString(string)

    fun toDouble(valor: String): Double {
        try {
            val numero = valor.replace(',', '.')
            return numero.toDouble()
        } catch (e: NumberFormatException) {
            return 0.0
        }

    }

}
