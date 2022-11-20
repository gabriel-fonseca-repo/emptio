package com.gabriel.augusto.emptio.database;

import android.content.Context;

import androidx.room.Room;

public class Db {

    private static LojaDatabase instancia = null;

    public static LojaDatabase getInstancia(Context applicationContext) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(applicationContext,
                    LojaDatabase.class, "lojadatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instancia;
    }
}
