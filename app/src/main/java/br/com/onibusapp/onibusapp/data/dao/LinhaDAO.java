package br.com.onibusapp.onibusapp.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.onibusapp.onibusapp.data.DBEnum;
import br.com.onibusapp.onibusapp.data.dominio.Linha;

/**
 * Created by diego on 18/07/2018.
 */

public class LinhaDAO {

    private static final String NOME_TABELA = "TB_LINHA";
    private static final String COD_EMPRESA = "COD_EMPRESA";
    private static final String ERRO_DAO_TB_LINHA = "[ERRO CRIAR DAO TB_LINHA]";
    private SQLiteDatabase sqLiteDatabase;

    public LinhaDAO(Context context) {
        try {
            sqLiteDatabase = context.openOrCreateDatabase(DBEnum.NOME.getDescricao(),
                    Context.MODE_PRIVATE, null);
        } catch (Exception e) {
            Log.e(ERRO_DAO_TB_LINHA, e.getMessage());
        }
    }

    public List<Linha> findAll() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + NOME_TABELA, null);
        return montarListaToCursor(cursor);
    }

    public List<Linha> findByCodigoEmpresa(Integer id) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COD_EMPRESA  + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[] { id.toString() });
        return montarListaToCursor(cursor);
    }

    private List<Linha> montarListaToCursor(Cursor cursor) {
        List<Linha> linhas = new ArrayList<>();
        if (cursor.moveToNext()) {
            do {
                Linha linha = new Linha();
                linha.setId(cursor.getInt(0));
                linha.setNumero(cursor.getString(1));
                linha.setCodigoEmpresa(cursor.getInt(2));
                linhas.add(linha);
            } while (cursor.moveToNext());
        }
        return linhas;
    }
}
