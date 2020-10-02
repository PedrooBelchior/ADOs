package com.gerenciadorDeProdutos.database;

/**
 * Created by pedro.saraujo on 29/09/2020.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelperNota extends SQLiteOpenHelper {

    public DadosOpenHelperNota(Context context) {
        super(context, "DADOSNOTAS2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execulta a ação para criar o banco de dados internamente no aparelho
        db.execSQL(ScriptDLLNota.getCreateTableNota());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
