package com.gabriel.augusto.emptio.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gabriel.augusto.emptio.R;
import com.gabriel.augusto.emptio.dto.ProdutoQuantidade;
import com.gabriel.augusto.emptio.entidades.Produto;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoQuantidadeAdapter extends ArrayAdapter<ProdutoQuantidade> {

    private final Context context;
    private final ProdutoQuantidade[] values;

    public ProdutoQuantidadeAdapter(@NonNull Context context, ProdutoQuantidade[] values) {
        super(context, R.layout.card_layout, values);
        this.context = context;
        this.values = values;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View cardView = inflater.inflate(R.layout.card_layout, parent, false);

        TextView tituloProduto = cardView.findViewById(R.id.titulo_produto);
        TextView descricaoProduto = cardView.findViewById(R.id.descricao_produto);
        TextView precoProduto = cardView.findViewById(R.id.preco_produto);
        TextView qtdProduto = cardView.findViewById(R.id.quantidade_produto);
        ImageView imagemProduto = cardView.findViewById(R.id.imagem_produto);

        Button botaoAdicionar = cardView.findViewById(R.id.botao_adicionar_produto);
        Button botaoRemover = cardView.findViewById(R.id.botao_remover_produto);

        ProdutoQuantidade val = values[position];
        tituloProduto.setText(val.getProduto().getNome());
        descricaoProduto.setText(val.getProduto().getDescricao());
        precoProduto.setText(Util.dinheiro(val.getProduto().getPreco()));
        qtdProduto.setText(Integer.toString(val.getQuantidade()));
        imagemProduto.setImageResource(val.getProduto().getImagem());
        cardView.setTag(val);

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Usuario usuarioLogado = Util.getUsuarioLogado(context);
                    ProdutoQuantidade prodQnt = (ProdutoQuantidade) cardView.getTag();

                    List<Integer> itensEscolhidos = usuarioLogado.getSacola().stream().map(ProdutoQuantidade::getProduto).map(Produto::getId).collect(Collectors.toList());

                    if (itensEscolhidos.contains(prodQnt.getProduto().getId())) {
                        int qtdAtual = prodQnt.getQuantidade();
                        prodQnt.setQuantidade(qtdAtual + 1);
                        int indexOfProdutoQuantidadeById = usuarioLogado.indexOfProdutoQuantidadeById(prodQnt.getProduto().getId());
                        usuarioLogado.getSacola().get(indexOfProdutoQuantidadeById).setQuantidade(qtdAtual + 1);
                    } else {
                        prodQnt.setQuantidade(1);
                        usuarioLogado.getSacola().add(prodQnt);
                    }

                    Util.setUsuarioLogado(context, usuarioLogado);

                    qtdProduto.setText(prodQnt.getQuantidade().toString());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Usuário não encontrado na sessão!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Usuario usuarioLogado = Util.getUsuarioLogado(context);
                    ProdutoQuantidade prodQnt = (ProdutoQuantidade) cardView.getTag();

                    List<Integer> itensEscolhidos = usuarioLogado.getSacola().stream().map(ProdutoQuantidade::getProduto).map(Produto::getId).collect(Collectors.toList());

                    if (itensEscolhidos.contains(prodQnt.getProduto().getId())) {
                        if (prodQnt.getQuantidade() > 0) {
                            prodQnt.setQuantidade(prodQnt.getQuantidade() - 1);
                        }
                        if (prodQnt.getQuantidade() == 0) {
                            usuarioLogado.getSacola().removeIf(prodQnt1 -> prodQnt.getProduto().getId() == prodQnt1.getProduto().getId());
                        }
                    }

                    Util.setUsuarioLogado(context, usuarioLogado);

                    qtdProduto.setText(prodQnt.getQuantidade().toString());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Usuário não encontrado na sessão!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return cardView;
    }
}
