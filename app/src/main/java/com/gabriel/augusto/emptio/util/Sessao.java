package com.gabriel.augusto.emptio.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.augusto.emptio.R;

public class Sessao {
    public static void put(String key, Object value, Context context) throws JsonProcessingException {

        String valueToStore;

        if (!(value instanceof String)) {
            ObjectMapper mapper = new ObjectMapper();
            valueToStore = mapper.writeValueAsString(value);
        } else {
            valueToStore = (String) value;
        }

        SharedPreferences.Editor prefEdit = context.getSharedPreferences(context.getString(R.string.usuario_logado_pref), Context.MODE_PRIVATE).edit();
        prefEdit.putString(key, valueToStore);
        prefEdit.apply();
    }

    public static <T> T get(String key, Context context, Class<T> valueType) throws JsonProcessingException {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.usuario_logado_pref), Context.MODE_PRIVATE);
        String usuario = prefs.getString(key, null);
        ObjectMapper objMp = new ObjectMapper();
        return objMp.readValue(usuario, valueType);
    }
}