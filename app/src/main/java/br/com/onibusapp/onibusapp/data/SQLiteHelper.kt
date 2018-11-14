package br.com.onibusapp.onibusapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by diego on 18/07/2018.
 */

class SQLiteHelper(context: Context, name: String, version: Int, private val scriptSQLCreate: Array<String>,
                   private val scriptSQLDelete: String) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val qtdScript = scriptSQLCreate.size
        for (i in 0 until qtdScript) {
            val sql = scriptSQLCreate[i]
            Log.i("SQL_INIT", sql)
            db.execSQL(sql)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAntiga: Int, versaoNova: Int) {
        db.execSQL(scriptSQLDelete)
        onCreate(db)

    }

    fun onDelete(db: SQLiteDatabase) {
        val qtdScript = scriptSQLCreate.size
        for (i in 0 until qtdScript) {
            val sql = scriptSQLCreate[i]
            db.execSQL(sql)
        }
    }
}
