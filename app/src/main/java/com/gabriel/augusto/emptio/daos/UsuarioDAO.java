package com.gabriel.augusto.emptio.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gabriel.augusto.emptio.entidades.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO {

    @Query("SELECT * FROM usuario")
    List<Usuario> getAll();

    @Query("SELECT EXISTS(SELECT * FROM usuario WHERE email = :email)")
    Boolean emailJaCadastrado(String email);

    @Query("SELECT * FROM usuario WHERE email = :email")
    Usuario getByEmail(String email);

    @Insert
    void save(Usuario usuario);

}
