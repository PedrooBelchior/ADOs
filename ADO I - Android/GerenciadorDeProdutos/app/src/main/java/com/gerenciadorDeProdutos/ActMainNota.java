package com.gerenciadorDeProdutos;;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.gerenciadorDeProdutos.database.DadosOpenHelperNota;
import com.gerenciadorDeProdutos.dominio.entidades.Nota;
import com.gerenciadorDeProdutos.dominio.repositorio.NotaRepositorio;
import java.util.List;

/**
 * Created by pedro.saraujo on 29/09/2020.
 */

public class ActMainNota extends AppCompatActivity {

    private RecyclerView lstDadosNota;
    private FloatingActionButton fab;
    private ConstraintLayout layoutContentMainNota;

    private SQLiteDatabase conexao;
    private DadosOpenHelperNota dadosOpenHelperNota;
    public List<Nota> dados;

    private NotaRepositorio notaRepositorio;

    private NotasAdapter notasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_nota);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        lstDadosNota = (RecyclerView) findViewById(R.id.lstDadosNota);
        layoutContentMainNota = (ConstraintLayout) findViewById(R.id.layoutContentMainNota);

        criarConexao();

        lstDadosNota.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosNota.setLayoutManager(linearLayoutManager);

        notaRepositorio = new NotaRepositorio(conexao);

        dados = notaRepositorio.buscarTodosNotas();

        notasAdapter = new NotasAdapter(dados);

        lstDadosNota.setAdapter(notasAdapter);
    }

    private void criarConexao(){
        try{
            dadosOpenHelperNota = new DadosOpenHelperNota(this);

            conexao = dadosOpenHelperNota.getWritableDatabase();


            /*Snackbar.make(layoutContentMain, R.string.message_conexao_criada_com_sucesso, Snackbar.LENGTH_SHORT)
            .setAction(R.string.lbl_ok, null).show();
*/
        }catch(SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    public void cadastrar(View view){
        Intent it = new Intent(ActMainNota.this, ActCadNota.class);
        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //retorna todos os dados do banco e o adapta na listView
        List<Nota> dados = notaRepositorio.buscarTodosNotas();
        notasAdapter = new NotasAdapter(dados);
        lstDadosNota.setAdapter(notasAdapter);
    }
}