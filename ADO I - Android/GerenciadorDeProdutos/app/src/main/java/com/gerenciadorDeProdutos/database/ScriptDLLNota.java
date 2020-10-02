package com.gerenciadorDeProdutos.database;

/**
 * Created by pedro.saraujo on 29/09/2020
 */

public class ScriptDLLNota {

    //Cria o banco de dados
    public static String getCreateTableNota(){
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS TB_NOTAS ( ");
        sql.append(" COD INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append(" PROD VARCHAR(250) NOT NULL DEFAULT (''), ");
        sql.append(" ENT FLOAT(255) NOT NULL DEFAULT (''), ");
        sql.append(" SAID FLOAT(200) NOT NULL DEFAULT (''), ");
        sql.append(" RES FLOAT(200) DEFAULT ('') ");
        sql.append(") ");

        return sql.toString();

    }

}

