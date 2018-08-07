package br.com.onibusapp.onibusapp.data.dominio;

import java.io.Serializable;

/**
 * Created by diego on 18/07/2018.
 */

public class Favorito implements Serializable {

    private Integer id;
    private Integer codigoLinha;
    private Integer codigoSentido;
    private String nomeLinha;
    private Integer codigoEmpresa;

    public Favorito() {}

    public Favorito(Integer codigoLinha, Integer codigoSentido) {
        this.codigoLinha = codigoLinha;
        this.codigoSentido = codigoSentido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(Integer codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public Integer getCodigoSentido() {
        return codigoSentido;
    }

    public void setCodigoSentido(Integer codigoSentido) {
        this.codigoSentido = codigoSentido;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    @Override
    public String toString() {
        return "Favorito {" +
                "id=" + id +
                ", codigoLinha=" + codigoLinha +
                ", codigoSentido=" + codigoSentido +
                ", nomeLinha=" + nomeLinha +
                ", codigoEmpresa=" + codigoEmpresa + "}";
    }
}
