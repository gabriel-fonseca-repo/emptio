package com.gabriel.augusto.emptio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gabriel.augusto.emptio.database.Db;
import com.gabriel.augusto.emptio.databinding.FragmentCartaoCadastroBinding;
import com.gabriel.augusto.emptio.entidades.Cartao;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

public class CartaoCadastroFragment extends Fragment {

    private FragmentCartaoCadastroBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCartaoCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botaoCadastroCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCartao();
            }
        });
    }

    private void validarCartao() {

        String numero = binding.cartaoCadastroNumero.getText().toString();
        String cvc = binding.cartaoCadastroCvc.getText().toString();
        String dataExpiracao = binding.cartaoCadastroDataExpiracao.getText().toString();
        String nome = binding.cartaoCadastroNomeCartao.getText().toString();

        if (numero.length() < 19) {
            Toast.makeText(getContext(), "Número inválido!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cvc.length() < 3) {
            Toast.makeText(getContext(), "CVC inválido!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataExpiracao.length() < 5 || !dataExpiracao.contains("/")) {
            Toast.makeText(getContext(), "Data inválida!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nome.isEmpty()) {
            Toast.makeText(getContext(), "Nome não preenchido!", Toast.LENGTH_SHORT).show();
            return;
        }

        Cartao cartao = new Cartao();
        cartao.setNomeNoCartao(nome);
        cartao.setCvc(Integer.parseInt(cvc));
        cartao.setDataExpiracao(dataExpiracao);
        cartao.setNumero(numero);

        try {
            Usuario usuarioLogado = Util.getUsuarioLogado(requireContext());
            cartao.setIdusuario(usuarioLogado.getId());

            Db.getInstancia(requireContext()).cartaoDAO().save(cartao);

            Toast.makeText(getContext(), "Cadastro do cartão realizado com sucesso!", Toast.LENGTH_SHORT).show();

            NavHostFragment.findNavController(CartaoCadastroFragment.this)
                    .navigate(R.id.cartao_cadastro_to_cartao);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro no cadastro do cartão!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}