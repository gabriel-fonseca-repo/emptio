package com.gabriel.augusto.emptio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gabriel.augusto.emptio.databinding.FragmentObrigadoBinding;
import com.gabriel.augusto.emptio.entidades.Usuario;
import com.gabriel.augusto.emptio.util.Util;

public class ObrigadoFragment extends Fragment {

    private FragmentObrigadoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentObrigadoBinding.inflate(inflater, container, false);

        setupTela();

        return binding.getRoot();
    }

    private void setupTela() {
        Usuario usuarioLogado = null;
        try {
            usuarioLogado = Util.getUsuarioLogado(requireContext());
            binding.nomeUsuarioObrigado.setText(usuarioLogado.getNome());
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