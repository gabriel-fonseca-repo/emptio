package com.gabriel.augusto.emptio.dto;

import com.gabriel.augusto.emptio.entidades.Produto;

public class ProdutoQuantidade {

    private Produto produto;

    private Integer quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
