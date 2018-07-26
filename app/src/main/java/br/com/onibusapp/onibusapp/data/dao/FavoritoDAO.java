package br.com.onibusapp.onibusapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.onibusapp.onibusapp.data.DBEnum;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;

/**
 * Created by diego on 18/07/2018.
 */

public class FavoritoDAO {

    private static final String NOME_TABELA = "TB_FAVORITOS";
    private static final String ID = "ID";
    private static final String COD_LINHA = "COD_LINHA";
    private static final String COD_SENTIDO = "COD_SENTIDO";
    private static final String ERRO_DAO_TB_FAVORITOS = "[ERRO CRIAR DAO TB_FAVORITOS]";
    private SQLiteDatabase sqLiteDatabase;

    public FavoritoDAO(Context context) {
        try {
            sqLiteDatabase = context.openOrCreateDatabase(DBEnum.NOME.getDescricao(),
                    Context.MODE_PRIVATE, null);
        } catch (Exception e) {
            Log.e(ERRO_DAO_TB_FAVORITOS, e.getMessage());
        }
    }

    public Favorito salvar(Favorito favorito) {
        if (isExiste(favorito)) {
            return atualizar(favorito);
        } else {
            return inserir(favorito);
        }
    }

    private Favorito inserir(Favorito favorito) {
        ContentValues values = criarInsertValues(favorito);
        Long id = inserir(values);
        favorito.setId(id.intValue());
        return favorito;
    }

    private Boolean isExiste(Favorito favorito) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COD_LINHA  + " = ? AND " + COD_SENTIDO + " = ? ";
        Cursor cursor = sqLiteDatabase.rawQuery(query,
                new String[] { favorito.getCodigoLinha().toString(), favorito.getCodigoSentido().toString() });

        return cursor.getCount() > 0;

    }

    private Long inserir(ContentValues valores) {
        return sqLiteDatabase.insert(NOME_TABELA, "", valores);
    }

    private Favorito atualizar(Favorito favorito) {
        ContentValues values = criarInsertValues(favorito);
        String where = COD_SENTIDO + " = ? AND " + COD_LINHA + " = ? ";
        String[] whereArgs = new String[]{ favorito.getCodigoSentido().toString(), favorito.getCodigoLinha().toString() };
        atualizar(values, where, whereArgs);
        return favorito;
    }

    private int atualizar(ContentValues values, String where, String[] whereArgs) {
        return sqLiteDatabase.update(NOME_TABELA, values, where, whereArgs);
    }

    private ContentValues criarInsertValues(Favorito favorito) {
        ContentValues values = new ContentValues();
        values.put(ID, favorito.getId());
        values.put(COD_LINHA, favorito.getCodigoLinha());
        values.put(COD_SENTIDO, favorito.getCodigoSentido());
        return values;
    }

    public List<Favorito> findAll() {
        String query = "SELECT F.ID, F.COD_LINHA, F.COD_SENTIDO, L.NUMERO FROM " + NOME_TABELA + " AS F INNER JOIN TB_LINHA AS L ON L.ID = F.COD_LINHA";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return montarListaToCursor(cursor);
    }

    private List<Favorito> montarListaToCursor(Cursor cursor) {
        List<Favorito> favoritos = new ArrayList<>();
        if (cursor.moveToNext()) {
            do {
                Favorito favorito = new Favorito();
                favorito.setId(cursor.getInt(0));
                favorito.setCodigoLinha(cursor.getInt(1));
                favorito.setCodigoSentido(cursor.getInt(2));
                favorito.setNomeLinha(cursor.getString(3));
                favoritos.add(favorito);
            } while (cursor.moveToNext());
        }
        return favoritos;
    }

    public void apagar(Integer id) {
        String[] whereArgs = new String[]{ id.toString() };
        sqLiteDatabase.delete(NOME_TABELA, ID + "= ? ", whereArgs);
    }
}
