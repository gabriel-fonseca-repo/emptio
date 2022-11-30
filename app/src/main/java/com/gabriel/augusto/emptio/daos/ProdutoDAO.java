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

    @Query("SELECT * FROM produto WHERE id IN (:listaProdutos)")
    List<Produto> listById(int[] listaProdutos);

    @Query("SELECT * FROM produto WHERE id NOT IN (:listaProdutos)")
    List<Produto> listByNotId(Integer[] listaProdutos);

    @Insert
    void save(Produto produto);

    @Query("DELETE FROM produto")
    void deleteAll();

}
