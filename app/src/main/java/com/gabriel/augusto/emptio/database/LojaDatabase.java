package com.gabriel.augusto.emptio.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gabriel.augusto.emptio.daos.UsuarioDAO;
import com.gabriel.augusto.emptio.entidades.Usuario;

@Database(entities = {Usuario.class}, version = 3, exportSchema = false)
public abstract class LojaDatabase extends RoomDatabase {

    public abstract UsuarioDAO usuarioDAO();
}
