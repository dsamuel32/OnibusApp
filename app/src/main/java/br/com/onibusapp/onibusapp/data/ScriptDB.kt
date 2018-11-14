package br.com.onibusapp.onibusapp.data

import android.content.Context

/**
 * Created by diego on 18/07/2018.
 */

class ScriptDB(context: Context) {

    private val dbHelper: SQLiteHelper

    fun retornaScript(): Int = SCRIPT_DATABASE_CREATEPADRAO.size

    init {
        dbHelper = SQLiteHelper(context, DBEnum.NOME.descricao,
                Integer.valueOf(DBEnum.VERSAO.descricao),
                SCRIPT_DATABASE_CREATEPADRAO, DBEnum.SCRIPT_DATABASE_DELETE.descricao)
        dbHelper.writableDatabase
    }

    fun fechar() {
        dbHelper.close()
    }

    companion object {

        private val SCRIPT_DATABASE_CREATEPADRAO = arrayOf("CREATE TABLE TB_LINHA(ID INTEGER PRIMARY KEY, " +
                "NUMERO VARCHAR(6), " +
                "COD_EMPRESA INTEGER); ",

                "CREATE TABLE TB_EMPRESA(ID INTEGER PRIMARY KEY, NOME VARCHAR(50));",

                "CREATE TABLE TB_FAVORITOS(ID INTEGER PRIMARY KEY, COD_LINHA INTEGER, COD_SENTIDO INTEGER);",

                "INSERT INTO TB_EMPRESA(NOME) VALUES('Marechal')", "INSERT INTO TB_EMPRESA(NOME) VALUES('Pioneira')", "INSERT INTO TB_EMPRESA(NOME) VALUES('Piracicabana')", "INSERT INTO TB_EMPRESA(NOME) VALUES('São José')",

                "INSERT INTO TB_LINHA(NUMERO, COD_EMPRESA) VALUES('0.006', 3)", "INSERT INTO TB_LINHA(NUMERO, COD_EMPRESA) VALUES('512.1', 3)", "INSERT INTO TB_LINHA(NUMERO, COD_EMPRESA) VALUES('0.512', 3)")
    }

}
