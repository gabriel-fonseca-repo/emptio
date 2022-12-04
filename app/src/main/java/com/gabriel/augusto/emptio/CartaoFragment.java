package com.gabriel.augusto.emptio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gabriel.augusto.emptio.adapter.CartaoAdapter;
import com.gabriel.augusto.emptio.database.Db;
import com.gabriel.augusto.emptio.databinding.FragmentCartaoBinding;
import com.gabriel.augusto.emptio.entidades.Cartao;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

import java.util.List;

public class CartaoFragment extends Fragment {

    private FragmentCartaoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartaoBinding.inflate(inflater, container, false);

        setupTela();

        return binding.getRoot();
    }

    private void setupTela() {
        Usuario usuarioLogado = null;
        try {
            usuarioLogado = Util.getUsuarioLogado(requireContext());
            List<Cartao> cartoes = Db.getInstancia(requireContext()).cartaoDAO().getByIdUsuario(usuarioLogado.getId());

            binding.listaCartoes.setAdapter(new CartaoAdapter(requireContext(), cartoes.toArray(new Cartao[0]), this));

            binding.botaoCadastrarCartao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(CartaoFragment.this)
                            .navigate(R.id.cartao_to_cartao_cadastro);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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