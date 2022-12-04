package com.gabriel.augusto.emptio.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cartao {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "idusuario")
    private int idusuario;

    @ColumnInfo(name = "numero")
    private String numero;

    @ColumnInfo(name = "nomeNoCartao")
    private String nomeNoCartao;

    @ColumnInfo(name = "dataExpiracao")
    private String dataExpiracao;

    @ColumnInfo(name = "cvc")
    private Integer cvc;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeNoCartao() {
        return nomeNoCartao;
    }

    public void setNomeNoCartao(String nomeNoCartao) {
        this.nomeNoCartao = nomeNoCartao;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}
