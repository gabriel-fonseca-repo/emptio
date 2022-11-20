package com.gabriel.augusto.emptio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.augusto.emptio.database.Db;
import com.gabriel.augusto.emptio.databinding.FragmentLoginBinding;
import com.gabriel.augusto.emptio.entidades.Usuario;

public class LoginFragment extends Fragment {

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
        ObjectMapper mapper = new ObjectMapper();

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

        try {
            String usuarioJson = mapper.writeValueAsString(usuario);
            SharedPreferences.Editor prefEdit = getActivity().getSharedPreferences(getString(R.string.usuario_logado_pref), Context.MODE_PRIVATE).edit();
            prefEdit.putString(getString(R.string.usuario_logado_pref), usuarioJson);
            prefEdit.apply();
        } catch (JsonProcessingException e) {
            String erro = "Erro na serialização do usuário!";
            System.out.println(erro);
            Toast.makeText(getContext(), erro, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}