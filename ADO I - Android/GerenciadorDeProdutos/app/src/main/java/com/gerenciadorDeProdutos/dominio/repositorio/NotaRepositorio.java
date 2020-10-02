package com.gerenciadorDeProdutos.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gerenciadorDeProdutos.dominio.entidades.Nota;

import java.util.ArrayList;
import java.util.List;

//todas as chamadas para o banco de dados estão aqui, menos a de criação
public class NotaRepositorio {

    private SQLiteDatabase conexao;

    public NotaRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Nota cliente) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PROD", cliente.produto);
        contentValues.put("ENT", cliente.entrada);
        contentValues.put("SAID", cliente.saida);
        contentValues.put("RES", cliente.result);

        conexao.insertOrThrow("TB_NOTAS", null, contentValues);
    }

    public void excluir(int cod) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(cod);

        conexao.delete("TB_NOTAS", "COD = ?", parametros);
    }

    public void alterar(Nota cliente) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PROD", cliente.produto);
        contentValues.put("ENT", cliente.entrada);
        contentValues.put("SAID", cliente.saida);
        contentValues.put("RES", cliente.result);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(cliente.cod);

        conexao.update("TB_NOTAS", contentValues, "COD = ?", parametros);
    }

    public List<Nota> buscarTodosNotas() {
        ArrayList<Nota> notas = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COD, PROD, ENT, SAID, RES ");
        sql.append(" FROM TB_NOTAS ");

        try (Cursor resultado = conexao.rawQuery(sql.toString(), null)) {

            if (resultado.getCount() > 0) {

                resultado.moveToFirst();

                do {
                    Nota cli = new Nota();

                    cli.cod = resultado.getInt(resultado.getColumnIndex("COD"));
                    cli.produto = resultado.getString(resultado.getColumnIndex("PROD"));
                    cli.entrada = resultado.getString(resultado.getColumnIndex("ENT"));
                    cli.saida = resultado.getString(resultado.getColumnIndex("SAID"));
                    cli.result = resultado.getString(resultado.getColumnIndex("RES"));

                    notas.add(cli);

                } while (resultado.moveToNext());
            }
        }

        return notas;
    }
}

