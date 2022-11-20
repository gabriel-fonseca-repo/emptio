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
import com.gabriel.augusto.emptio.databinding.FragmentCadastroBinding;
import com.gabriel.augusto.emptio.entidades.Usuario;

public class CadastroFragment extends Fragment {

    private FragmentCadastroBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCadastro();
            }
        });
    }

    private void validarCadastro() {
        System.out.println("Cadastro...");

        String nomeUsuario = binding.cadastroNome.getText().toString();
        String emailUsuario = binding.cadastroEmail.getText().toString();
        String senha = binding.cadastroSenha.getText().toString();
        String confirmacaoSenha = binding.cadastroConfirmeSenha.getText().toString();

        if (!senha.equals(confirmacaoSenha)) {
            Toast.makeText(getContext(), "As senhas são diferentes!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!emailUsuario.contains("@")) {
            Toast.makeText(getContext(), "Email inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Db.getInstancia(getContext()).usuarioDAO().emailJaCadastrado(emailUsuario)) {
            Toast.makeText(getContext(), "Email já cadastrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuarioEmCadastro = new Usuario();
        usuarioEmCadastro.setNome(nomeUsuario);
        usuarioEmCadastro.setEmail(emailUsuario);
        usuarioEmCadastro.setSenha(senha);

        Db.getInstancia(getContext()).usuarioDAO().save(usuarioEmCadastro);

        Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

        NavHostFragment.findNavController(CadastroFragment.this)
                .navigate(R.id.cadastro_to_login);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}