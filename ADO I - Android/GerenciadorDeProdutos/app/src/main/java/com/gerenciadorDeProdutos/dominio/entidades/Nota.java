package com.gerenciadorDeProdutos.dominio.entidades;

/**
 * Created by pedro.saraujo on 29/09/2020
 */

import java.io.Serializable;

//Objeto Nota
public class Nota implements Serializable{

    public int cod;
    public String produto;
    public String entrada;
    public String saida;
    public String result;
}
