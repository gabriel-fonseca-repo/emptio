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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private final Context context;
    private final Produto[] values;

    public ProdutoAdapter(@NonNull Context context, Produto[] values) {
        super(context, R.layout.card_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View cardView = inflater.inflate(R.layout.card_layout, parent, false);

        TextView tituloProduto = (TextView) cardView.findViewById(R.id.titulo_produto);
        TextView descricaoProduto = (TextView) cardView.findViewById(R.id.descricao_produto);
        TextView precoProduto = (TextView) cardView.findViewById(R.id.preco_produto);
        TextView qtdProduto = (TextView) cardView.findViewById(R.id.quantidade_produto);
        ImageView imagemProduto = (ImageView) cardView.findViewById(R.id.imagem_produto);

        Button botaoAdicionar = (Button) cardView.findViewById(R.id.botao_adicionar_produto);
        Button botaoRemover = (Button) cardView.findViewById(R.id.botao_remover_produto);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("R$ #,###.00", symbols);

        Produto val = values[position];
        tituloProduto.setText(val.getNome());
        descricaoProduto.setText(val.getDescricao());
        precoProduto.setText(decimalFormat.format(val.getPreco()));
        imagemProduto.setImageResource(val.getImagem());

        ProdutoQuantidade produtoQuantidade = new ProdutoQuantidade();
        produtoQuantidade.setProduto(val);
        produtoQuantidade.setQuantidade(0);
        cardView.setTag(produtoQuantidade);

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Usuario usuarioLogado = Util.getUsuarioLogado(context);
                    ProdutoQuantidade prodQnt = (ProdutoQuantidade) cardView.getTag();

                    List<Integer> itensEscolhidos = usuarioLogado.getSacola().stream().map(ProdutoQuantidade::getProduto).map(Produto::getId).collect(Collectors.toList());

                    if (itensEscolhidos.contains(prodQnt.getProduto().getId())) {
                        prodQnt.setQuantidade(prodQnt.getQuantidade() + 1);
                    } else {
                        System.out.println("adicionando mais um produto");
                        prodQnt.setQuantidade(1);
                        usuarioLogado.getSacola().add(prodQnt);
                    }

                    System.out.println(usuarioLogado.getSacola());

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
