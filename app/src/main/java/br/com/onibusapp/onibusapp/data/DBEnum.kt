package br.com.onibusapp.onibusapp.data

/**
 * Created by diego on 18/07/2018.
 */

enum class DBEnum private constructor(val descricao: String) {

    SCRIPT_DATABASE_DELETE("DROP TABLE IF EXISTS TB_EMPRESA; DROP TABLE IF EXISTS TB_LINHA;"),
    NOME("ONIBUS_APP"),
    VERSAO("1")

}
