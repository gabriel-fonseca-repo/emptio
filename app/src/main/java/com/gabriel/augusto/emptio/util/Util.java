package com.gabriel.augusto.emptio.util;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gabriel.augusto.emptio.R;
import com.gabriel.augusto.emptio.entidades.Usuario;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Util {

    public static Usuario getUsuarioLogado(Context context) throws JsonProcessingException {
        return Sessao.get(context.getString(R.string.usuario_logado_pref), context, Usuario.class);
    }

    public static void setUsuarioLogado(Context context, Usuario usuario) throws JsonProcessingException {
        Sessao.put(context.getString(R.string.usuario_logado_pref), usuario, context);
    }

    public static String dinheiro(double toConvert) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("R$ #,###.00", symbols);
        return decimalFormat.format(toConvert);
    }


}
