package com.gabriel.augusto.emptio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gabriel.augusto.emptio.adapter.ProdutoQuantidadeAdapter;
import com.gabriel.augusto.emptio.database.Db;
import com.gabriel.augusto.emptio.databinding.FragmentLojaBinding;
import com.gabriel.augusto.emptio.dto.ProdutoQuantidade;
import com.gabriel.augusto.emptio.entidades.Produto;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LojaFragment extends Fragment {

    private FragmentLojaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLojaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            setupTela();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Não foi possível recuperar sessão!", Toast.LENGTH_SHORT).show();
        }

        binding.botaoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LojaFragment.this)
                        .navigate(R.id.loja_to_checkout);
            }
        });
    }

    @Override
    public void onResume() {
        try {
            setupTela();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Não foi possível recuperar sessão!", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    private void setupTela() throws Exception {
        ArrayList<ProdutoQuantidade> produtosZerados = inicializarLista(Db.getInstancia(requireContext()).produtoDAO().getAll());
        ArrayList<ProdutoQuantidade> produtoQuantidades = new ArrayList<>();
        Usuario usrLogado = Util.getUsuarioLogado(requireContext());

        List<Integer> idProdutosSelecionados = usrLogado.getSacola().stream().map(ProdutoQuantidade::getProduto).map(Produto::getId).collect(Collectors.toList());
        for (ProdutoQuantidade produtoQuantidade : produtosZerados) {
            if (!idProdutosSelecionados.contains(produtoQuantidade.getProduto().getId())) {
                produtoQuantidades.add(produtoQuantidade);
            }
        }

        produtoQuantidades.addAll(usrLogado.getSacola());

        binding.listaProdutos.setAdapter(new ProdutoQuantidadeAdapter(requireContext(), produtoQuantidades.toArray(new ProdutoQuantidade[0])));
    }

    private ArrayList<ProdutoQuantidade> inicializarLista(List<Produto> produtos) {
        ArrayList<ProdutoQuantidade> produtoQuantidades = new ArrayList<>();
        for (Produto produto : produtos) {
            ProdutoQuantidade produtoQuantidade = new ProdutoQuantidade();
            produtoQuantidade.setQuantidade(0);
            produtoQuantidade.setProduto(produto);
            produtoQuantidades.add(produtoQuantidade);
        }
        return produtoQuantidades;
    }

}