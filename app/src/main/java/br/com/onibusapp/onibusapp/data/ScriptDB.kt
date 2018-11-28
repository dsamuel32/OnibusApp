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
        private val SCRIPT_DATABASE_CREATEPADRAO = arrayOf("CREATE TABLE TB_FAVORITOS(ID INTEGER PRIMARY KEY, LINHA VARCHAR(255), COD_SENTIDO INTEGER, URL VARCHAR(255));")
    }

}
