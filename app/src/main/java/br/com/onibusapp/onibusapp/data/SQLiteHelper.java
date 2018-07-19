package br.com.onibusapp.onibusapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by diego on 18/07/2018.
 */

public class SQLiteHelper  extends SQLiteOpenHelper {

    private String[] scriptSQLCreate;
    private String scriptSQLDelete;

    public SQLiteHelper(Context context, String name, int version, String[] scriptSQLCreate,
                        String scriptSQLDelete) {
        super(context, name, null, version);
        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptSQLDelete = scriptSQLDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int qtdScript = scriptSQLCreate.length;
        for (int i = 0; i < qtdScript; i++) {
            String sql = scriptSQLCreate[i];
            Log.i("SQL_INIT", sql);
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        db.execSQL(scriptSQLDelete);
        onCreate(db);

    }

    public void onDelete(SQLiteDatabase db) {
        int qtdScript = scriptSQLCreate.length;
        for (int i = 0; i < qtdScript; i++) {
            String sql = scriptSQLCreate[i];
            db.execSQL(sql);
        }
    }
}
