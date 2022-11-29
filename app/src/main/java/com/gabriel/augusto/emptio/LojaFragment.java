package com.gabriel.augusto.emptio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gabriel.augusto.emptio.adapter.ProdutoAdapter;
import com.gabriel.augusto.emptio.database.Db;
import com.gabriel.augusto.emptio.databinding.FragmentLojaBinding;
import com.gabriel.augusto.emptio.entidades.Produto;

import java.util.List;


public class LojaFragment extends Fragment {

    private FragmentLojaBinding binding;

    private ProdutoAdapter produtoAdapter;

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

        List<Produto> produtos = Db.getInstancia(requireContext()).produtoDAO().getAll();
        if (!produtos.isEmpty()) {
            produtoAdapter = new ProdutoAdapter(requireContext(),
                    produtos.toArray(new Produto[]{}));
            binding.listaProdutos.setAdapter(produtoAdapter);
        }
    }

}