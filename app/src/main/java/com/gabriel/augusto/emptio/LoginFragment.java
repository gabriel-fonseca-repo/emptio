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
import com.gabriel.augusto.emptio.databinding.FragmentLoginBinding;
import com.gabriel.augusto.emptio.entidades.Produto;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private static final int[] imgs = {R.drawable.relogio, R.drawable.cellphone, R.drawable.controller, R.drawable.ps5, R.drawable.pincel};
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Db.getInstancia(requireContext()).produtoDAO().deleteAll();

        Faker fk = new Faker();
        for (int imagem : imgs) {
            Produto prd = new Produto();
            prd.setDescricao(fk.lorem().characters(25));
            prd.setPreco(fk.number().randomDouble(2, 0, 60));
            prd.setNome(fk.commerce().productName());
            prd.setImagem(imagem);
            Db.getInstancia(requireContext()).produtoDAO().save(prd);
        }

        binding.botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.login_to_cadastro);
            }
        });

        binding.botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarLogin();
            }
        });
    }

    private void validarLogin() {
        String emailUsuario = binding.loginEmail.getText().toString();
        String senhaUsuario = binding.loginSenha.getText().toString();

        if (senhaUsuario.isEmpty() || emailUsuario.isEmpty()) {
            Toast.makeText(getContext(), "Credenciais não preenchidas corretamente!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = Db.getInstancia(getContext()).usuarioDAO().getByEmail(emailUsuario);

        if (usuario == null || usuario.getEmail() == null) {
            Toast.makeText(getContext(), "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!usuario.getSenha().equals(senhaUsuario)) {
            Toast.makeText(getContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
            return;
        }

        usuario.setSacola(new ArrayList<>());

        try {
            Util.setUsuarioLogado(requireContext(), usuario);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro na serialização do usuário!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.login_to_loja);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}