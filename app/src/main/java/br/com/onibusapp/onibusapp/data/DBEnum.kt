package br.com.onibusapp.onibusapp.data

/**
 * Created by diego on 18/07/2018.
 */

enum class DBEnum private constructor(val descricao: String) {

    SCRIPT_DATABASE_DELETE("DROP TABLE IF EXISTS TB_FAVORITOS;"),
    NOME("ONIBUS_APP"),
    VERSAO("2")

}
