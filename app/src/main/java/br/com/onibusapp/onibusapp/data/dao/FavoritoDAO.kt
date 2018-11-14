package br.com.onibusapp.onibusapp.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import java.util.ArrayList

import br.com.onibusapp.onibusapp.data.DBEnum
import br.com.onibusapp.onibusapp.data.dominio.Favorito

/**
 * Created by diego on 18/07/2018.
 */

class FavoritoDAO(context: Context) {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    init {
        try {
            sqLiteDatabase = context.openOrCreateDatabase(DBEnum.NOME.descricao,
                    Context.MODE_PRIVATE, null)
        } catch (e: Exception) {
            Log.e(ERRO_DAO_TB_FAVORITOS, e.message)
        }

    }

    fun salvar(favorito: Favorito): Favorito {
        return if (isExiste(favorito)) {
            atualizar(favorito)
        } else {
            inserir(favorito)
        }
    }

    private fun inserir(favorito: Favorito): Favorito {
        val values = criarInsertValues(favorito)
        val id = inserir(values)
        favorito.id = id.toInt()
        return favorito
    }

    private fun isExiste(favorito: Favorito): Boolean {
        val query = "SELECT * FROM $NOME_TABELA WHERE $COD_LINHA = ? AND $COD_SENTIDO = ? "
        val cursor = sqLiteDatabase.rawQuery(query,
                arrayOf(favorito.codigoLinha!!.toString(), favorito.codigoSentido!!.toString()))

        return cursor.count > 0

    }

    private fun inserir(valores: ContentValues): Long {
        return sqLiteDatabase.insert(NOME_TABELA, "", valores)
    }

    private fun atualizar(favorito: Favorito): Favorito {
        val values = criarInsertValues(favorito)
        val where = "$COD_SENTIDO = ? AND $COD_LINHA = ? "
        val whereArgs = arrayOf(favorito.codigoSentido!!.toString(), favorito.codigoLinha!!.toString())
        atualizar(values, where, whereArgs)
        return favorito
    }

    private fun atualizar(values: ContentValues, where: String, whereArgs: Array<String>): Int {
        return sqLiteDatabase.update(NOME_TABELA, values, where, whereArgs)
    }

    private fun criarInsertValues(favorito: Favorito): ContentValues {
        val values = ContentValues()
        values.put(ID, favorito.id)
        values.put(COD_LINHA, favorito.codigoLinha)
        values.put(COD_SENTIDO, favorito.codigoSentido)
        return values
    }

    fun findAll(): MutableList<Favorito> {
        val query = "SELECT F.ID, F.COD_LINHA, F.COD_SENTIDO, L.NUMERO, L.COD_EMPRESA FROM $NOME_TABELA AS F INNER JOIN TB_LINHA AS L ON L.ID = F.COD_LINHA"
        val cursor = sqLiteDatabase.rawQuery(query, null)
        return montarListaToCursor(cursor)
    }

    private fun montarListaToCursor(cursor: Cursor): MutableList<Favorito> {
        val favoritos = ArrayList<Favorito>()
        if (cursor.moveToNext()) {
            do {
                val favorito = Favorito()
                favorito.id = cursor.getInt(0)
                favorito.codigoLinha = cursor.getInt(1)
                favorito.codigoSentido = cursor.getInt(2)
                favorito.nomeLinha = cursor.getString(3)
                favorito.codigoEmpresa = cursor.getInt(4)
                favoritos.add(favorito)
            } while (cursor.moveToNext())
        }
        return favoritos
    }

    fun apagar(id: Int?) {
        val whereArgs = arrayOf(id!!.toString())
        sqLiteDatabase.delete(NOME_TABELA, "$ID= ? ", whereArgs)
    }

    companion object {

        val NOME_TABELA = "TB_FAVORITOS"
        val ID = "ID"
        val COD_LINHA = "COD_LINHA"
        val COD_SENTIDO = "COD_SENTIDO"
        val ERRO_DAO_TB_FAVORITOS = "[ERRO CRIAR DAO TB_FAVORITOS]"
    }
}
