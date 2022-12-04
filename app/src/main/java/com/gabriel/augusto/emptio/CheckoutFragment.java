package com.gabriel.augusto.emptio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
        setupTela();
    }

    @SuppressLint("SetTextI18n")
    private void setupTela() {
        try {
            Usuario usuarioLogado = Util.getUsuarioLogado(requireContext());
            List<ProdutoQuantidade> produtos = usuarioLogado.getSacola();
            double total = produtos.stream().mapToDouble(e -> (e.getQuantidade() * e.getProduto().getPreco())).sum();
            binding.total.setText("Total: " + Util.dinheiro(total));

            binding.listaSacola.setAdapter(new ProdutoQuantidadeAdapter(requireContext(), produtos.toArray(new ProdutoQuantidade[0])));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro calculando o total!", Toast.LENGTH_SHORT).show();
        }

        binding.botaoRealizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CheckoutFragment.this)
                        .navigate(R.id.checkout_to_cartao);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}