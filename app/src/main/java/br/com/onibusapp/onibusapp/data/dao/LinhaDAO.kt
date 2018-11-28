package br.com.onibusapp.onibusapp.data.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import java.util.ArrayList

import br.com.onibusapp.onibusapp.data.DBEnum
import br.com.onibusapp.onibusapp.data.dominio.Linha

/**
 * Created by diego on 18/07/2018.
 */

class LinhaDAO(context: Context) {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    init {
        try {
            sqLiteDatabase = context.openOrCreateDatabase(DBEnum.NOME.descricao,
                    Context.MODE_PRIVATE, null)
        } catch (e: Exception) {
            Log.e(ERRO_DAO_TB_LINHA, e.message)
        }

    }

    fun findAll(): List<Linha> {
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $NOME_TABELA", null)
        return montarListaToCursor(cursor)
    }

    fun findByCodigoEmpresa(id: Int?): List<Linha> {
        val query = "SELECT * FROM $NOME_TABELA WHERE $COD_EMPRESA = ?"
        val cursor = sqLiteDatabase.rawQuery(query, arrayOf(id!!.toString()))
        return montarListaToCursor(cursor)
    }

    private fun montarListaToCursor(cursor: Cursor): List<Linha> {
        val linhas = ArrayList<Linha>()
        if (cursor.moveToNext()) {
            do {
                val linha = Linha(cursor.getInt(0), cursor.getString(1), "", cursor.getInt(2))
                linhas.add(linha)
            } while (cursor.moveToNext())
        }
        return linhas
    }

    companion object {

        val NOME_TABELA = "TB_LINHA"
        val COD_EMPRESA = "COD_EMPRESA"
        val ERRO_DAO_TB_LINHA = "[ERRO CRIAR DAO TB_LINHA]"
    }
}
