package com.gabriel.augusto.emptio.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gabriel.augusto.emptio.daos.ProdutoDAO;
import com.gabriel.augusto.emptio.daos.UsuarioDAO;
import com.gabriel.augusto.emptio.entidades.Produto;
import com.gabriel.augusto.emptio.entidades.Usuario;

@Database(entities = {Usuario.class, Produto.class}, version = 4, exportSchema = false)
public abstract class LojaDatabase extends RoomDatabase {

    public abstract UsuarioDAO usuarioDAO();

    public abstract ProdutoDAO produtoDAO();
}
