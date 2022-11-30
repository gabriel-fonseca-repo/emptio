package com.gabriel.augusto.emptio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gabriel.augusto.emptio.adapter.ProdutoQuantidadeAdapter;
import com.gabriel.augusto.emptio.databinding.FragmentCheckoutBinding;
import com.gabriel.augusto.emptio.dto.ProdutoQuantidade;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

import java.util.List;

public class CheckoutFragment extends Fragment {

    private FragmentCheckoutBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Usuario usuarioLogado = Util.getUsuarioLogado(requireContext());
            List<ProdutoQuantidade> produtos = usuarioLogado.getSacola();
            double total = produtos.stream().mapToDouble(e -> (e.getQuantidade() * e.getProduto().getPreco())).sum();
            binding.total.setText(binding.total.getText() + " " + Util.dinheiro(total));

            binding.listaSacola.setAdapter(new ProdutoQuantidadeAdapter(requireContext(), produtos.toArray(new ProdutoQuantidade[0])));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro calculando o total!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}