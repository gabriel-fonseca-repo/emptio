package com.gabriel.augusto.emptio.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.gabriel.augusto.emptio.R;
import com.gabriel.augusto.emptio.entidades.Cartao;

public class CartaoAdapter extends ArrayAdapter<Cartao> {

    private final Context context;
    private final Fragment fragment;
    private final Cartao[] values;

    public CartaoAdapter(@NonNull Context context, Cartao[] values, Fragment fragment) {
        super(context, R.layout.card_layout, values);
        this.context = context;
        this.fragment = fragment;
        this.values = values;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View cardView = inflater.inflate(R.layout.lista_cartao_layout, parent, false);

        TextView numero = cardView.findViewById(R.id.numero);
        TextView cvc = cardView.findViewById(R.id.cvc);
        TextView dataExpiracao = cardView.findViewById(R.id.data_expiracao);

        View cartaoCard = cardView.findViewById(R.id.cartao_card);

        Cartao val = values[position];
        numero.setText(val.getNumero());
        cvc.setText(Integer.toString(val.getCvc()));
        dataExpiracao.setText(val.getDataExpiracao());

        cardView.setTag(val);

        cartaoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cartão selecionado: " + ((Cartao) cardView.getTag()).getNomeNoCartao(), Toast.LENGTH_LONG).show();

                NavHostFragment.findNavController(fragment).navigate(R.id.cartao_to_obrigado);
            }
        });

        return cardView;
    }
}
