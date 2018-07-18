package br.com.onibusapp.onibusapp.domain;

import java.io.Serializable;

/**
 * Created by diego on 17/07/2018.
 */

public class Filtro implements Serializable {

    private String linha;
    private Integer sentido;
    private Boolean adicionarFavoritos;

    public Filtro(String linha, Integer sentido, Boolean adicionarFavoritos) {
        this.linha = linha;
        this.sentido = sentido;
        this.adicionarFavoritos = adicionarFavoritos;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public Integer getSentido() {
        return sentido;
    }

    public void setSentido(Integer sentido) {
        this.sentido = sentido;
    }

    public Boolean getAdicionarFavoritos() {
        return adicionarFavoritos;
    }

    public void setAdicionarFavoritos(Boolean adicionarFavoritos) {
        this.adicionarFavoritos = adicionarFavoritos;
    }

    @Override
    public String toString() {
        return "Filtro{" +
                "linha='" + linha + '\'' +
                ", sentido=" + sentido +
                ", adicionarFavoritos=" + adicionarFavoritos +
                '}';
    }
}
