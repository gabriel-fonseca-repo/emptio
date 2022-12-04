package com.gabriel.augusto.emptio.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gabriel.augusto.emptio.entidades.Cartao;

import java.util.List;

@Dao
public interface CartaoDAO {

    @Query("SELECT * FROM cartao")
    List<Cartao> getAll();

    @Query("SELECT * FROM cartao WHERE idusuario = :idusuario")
    List<Cartao> getByIdUsuario(int idusuario);

    @Insert
    void save(Cartao cartao);

    @Query("DELETE FROM cartao")
    void deleteAll();

}
