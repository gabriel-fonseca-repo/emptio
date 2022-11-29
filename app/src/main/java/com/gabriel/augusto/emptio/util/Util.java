package com.gabriel.augusto.emptio.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.augusto.emptio.R;
import com.gabriel.augusto.emptio.entidades.Usuario;

public class Util {

    public static Usuario getUsuarioLogado(Context context) throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.usuario_logado_pref), Context.MODE_PRIVATE);
        String usuario = prefs.getString(context.getString(R.string.usuario_logado_pref), null);
        ObjectMapper objMp = new ObjectMapper();
        return objMp.readValue(usuario, Usuario.class);
    }

    public static void setUsuarioLogado(Context context, Usuario usuario) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String usuarioJson = mapper.writeValueAsString(usuario);
        SharedPreferences.Editor prefEdit = context.getSharedPreferences(context.getString(R.string.usuario_logado_pref), Context.MODE_PRIVATE).edit();
        prefEdit.putString(context.getString(R.string.usuario_logado_pref), usuarioJson);
        prefEdit.apply();
    }
}
