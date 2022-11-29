package com.gabriel.augusto.emptio.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gabriel.augusto.emptio.entidades.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {

    @Query("SELECT * FROM produto")
    List<Produto> getAll();

    @Insert
    void save(Produto produto);

    @Query("DELETE FROM produto")
    void deleteAll();

}
