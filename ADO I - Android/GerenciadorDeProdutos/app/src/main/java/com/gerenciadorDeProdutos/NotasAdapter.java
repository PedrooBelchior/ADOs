package com.gerenciadorDeProdutos;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gerenciadorDeProdutos.dominio.entidades.Nota;

import java.util.List;

/**
 * Created by pedro.saraujo on 29/09/2020.
 */

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolderNota> {

    private List<Nota> dadosn;
    public Nota notaSelecionado;

    public NotasAdapter(List<Nota> dados) {
        this.dadosn = dados;
    }

    @NonNull
    @Override
    public ViewHolderNota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_nota, parent, false);
        return new ViewHolderNota(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNota holder, int position) {

        if ((dadosn != null) && (dadosn.size() > 0) ){

            Nota cliente = dadosn.get(position);

            holder.txtProduto.setText("Nome: "+cliente.produto);
            holder.txtEntrada.setText("Preço de Custo: R$"+cliente.entrada);
            holder.txtSaida.setText("Preço de Venda: R$"+cliente.saida);
        }
    }

    @Override
    public int getItemCount() {
        return dadosn.size();
    }

    public class ViewHolderNota extends RecyclerView.ViewHolder{

        public TextView txtProduto;
        public TextView txtEntrada;
        public TextView txtSaida;


        private ViewHolderNota(View itemView, final Context context) {
            super(itemView);

            txtProduto = (TextView) itemView.findViewById(R.id.txtProduto);
            txtEntrada = (TextView) itemView.findViewById(R.id.txtEntrada);
            txtSaida = (TextView) itemView.findViewById(R.id.txtSaida);


            //quando algum item da lista é clicado, ele é mandado para a próxima tela para ser alterado ou excluido
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (dadosn.size() > 0){

                        notaSelecionado = dadosn.get(getLayoutPosition());

                        Intent it = new Intent(context, ActCadNota.class);
                        it.putExtra("TB_NOTAS", notaSelecionado);

                        ((AppCompatActivity) context).startActivityForResult(it, 0);

                    }
                }
            });
        }
    }
}