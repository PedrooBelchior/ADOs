package com.gerenciadorDeProdutos;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.gerenciadorDeProdutos.database.DadosOpenHelperNota;
import com.gerenciadorDeProdutos.dominio.entidades.Nota;
import com.gerenciadorDeProdutos.dominio.repositorio.NotaRepositorio;

/**
 * Created by pedro.saraujo on 29/09/2020.
 */

public class ActCadNota extends AppCompatActivity implements View.OnClickListener {

    //objetos do layout
    private EditText edtProduto;
    private EditText edtEntrada;
    private EditText edtSaida;
    private EditText edtResult;
    private ConstraintLayout layoutContentActCadNota;

    //objeto de controle do SQLite
    private SQLiteDatabase conexao;

    //objeto de acesso para criação do bano de dados
    private DadosOpenHelperNota dadosOpenHelperNota;

    //objeto de controle das querys de acesso do banco de dados
    private NotaRepositorio notaRepositorio;

    //objeto Cliente
    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_nota);
        init();

        //Cria a barra de ferramentas no layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nota = new Nota();
        edtProduto = (EditText) findViewById(R.id.edtProduto);
        edtEntrada = (EditText) findViewById(R.id.edtEntrada);
        edtSaida = (EditText) findViewById(R.id.edtSaida);
        edtResult = (EditText) findViewById(R.id.edtResult);
        layoutContentActCadNota = (ConstraintLayout) findViewById(R.id.layoutContentActCadNota);

        criarConexao();
        verificaParametro();

    }

    private void verificaParametro(){
        Bundle bundle = getIntent().getExtras();
        if ((bundle != null) && (bundle.containsKey("TB_NOTAS"))){
            Nota nota = (Nota) bundle.getSerializable("TB_NOTAS");
            this.nota.cod = nota.cod;
            edtProduto.setText(nota.produto);
            edtEntrada.setText(nota.entrada);
            edtSaida.setText(nota.saida);
            edtResult.setText(nota.result);
        }
    }

    //valida os campos, verificando se algum está vazio
    private boolean validaCampos() {

        boolean res = false;

        String produto = edtProduto.getText().toString();
        String entrada = edtEntrada.getText().toString();
        String saida = edtSaida.getText().toString();


        if (res = isCampoVazio(produto)) {
            edtProduto.requestFocus();
        } else if (res = isCampoVazio(entrada)) {
            edtEntrada.requestFocus();
        } else if (res = isCampoVazio(saida)) {
            edtSaida.requestFocus();
        }

        if (res) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_aviso);
            builder.setMessage(R.string.message_campos_invalidos_brancos);
            builder.setNeutralButton(R.string.lbl_ok, null);
            builder.show();
        }else{

            this.nota.produto = produto;
            this.nota.entrada = entrada;
            this.nota.saida = saida;
        }

        return res;

    }

    private boolean isCampoVazio(String valor) {

        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    //cria o menu superior utilizando o layout pré-construido
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_act_cad_nota, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //realiza alguma ação quando o usuário clica em uma opção do menu superior
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.act_ok:
                //validaCampos();
                confirmar();
//                Toast.makeText(this, "Botão Inserir selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.act_excluir:
                //validaCampos();
                excluir();
//                Toast.makeText(this, "Botão Excluir selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.act_alterar:
                //validaCampos();
                alterar();
//                Toast.makeText(this, "Botão Alterar selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.act_cancelar:
//                Toast.makeText(this, "Botão Cancelar selecionado", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //cria a conexão como banco de dados

    private void criarConexao(){
        try{
            dadosOpenHelperNota = new DadosOpenHelperNota(this);

            conexao = dadosOpenHelperNota.getWritableDatabase();

            Snackbar.make(layoutContentActCadNota, R.string.message_conexao_criada_com_sucesso, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.lbl_ok, null).show();

            notaRepositorio = new NotaRepositorio(conexao);

        }catch(SQLException ex){
            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    //insere um cliente no banco
    private void confirmar(){

        if (validaCampos() == false){

            try{
                notaRepositorio.inserir(nota);
                finish();
            }catch (SQLiteException ex){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_erro);
                builder.setMessage(ex.getMessage());
                builder.setNeutralButton(R.string.lbl_ok, null);
                builder.show();
            }

        }

    }

    //exclui um cliente do banco
    private void excluir(){

        if (validaCampos() == false){

            try{
                notaRepositorio.excluir(nota.cod);

                finish();
            }catch (SQLiteException ex){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_erro);
                builder.setMessage(ex.getMessage());
                builder.setNeutralButton(R.string.lbl_ok, null);
                builder.show();
            }

        }

    }

    //altera um cliente no banco
    private void alterar(){

        if (validaCampos() == false){

            try{
                notaRepositorio.alterar(nota);

                finish();
            }catch (SQLiteException ex){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_erro);
                builder.setMessage(ex.getMessage());
                builder.setNeutralButton(R.string.lbl_ok, null);
                builder.show();
            }

        }

    }

    //Calcula o resultado do dia
    private Button btnsub;
    private EditText etfirst,etsecond;
    private void init() {
        btnsub = (Button)findViewById(R.id.btnSubtract);
        etfirst = (EditText)findViewById(R.id.edtEntrada);
        etsecond =(EditText)findViewById(R.id.edtSaida);
        edtResult = (EditText)findViewById(R.id.edtResult);
        btnsub.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String num1 = etsecond.getText().toString();
        String num2 = etfirst.getText().toString();
        switch(view.getId()){
            case R.id.btnSubtract:
                int subtraction = Integer.parseInt(num1) - Integer.parseInt(num2);
                edtResult.setText(String.valueOf(subtraction));
                break;

        }
    }
}
