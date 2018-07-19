package br.com.onibusapp.onibusapp.data.dominio;

import java.io.Serializable;

/**
 * Created by diego on 15/07/2018.
 */

public class Linha implements Serializable {

    private Integer id;
    private String numero;
    private Integer codigoEmpresa;

    public Linha() {}

    public Linha(Integer id, String numero, Integer codigoEmpresa) {
        this.id = id;
        this.numero = numero;
        this.codigoEmpresa = codigoEmpresa;
    }

    public Linha(String numero, Integer codigoEmpresa) {
        this.numero = numero;
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

}
