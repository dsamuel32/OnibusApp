package br.com.onibusapp.onibusapp.domain;

import java.io.Serializable;

/**
 * Created by diego on 15/07/2018.
 */

public class Linha implements Serializable {

    private String numero;
    private Integer codigoEmpresa;


    public Linha(String numero, Integer codigoEmpresa) {
        this.numero = numero;
        this.codigoEmpresa = codigoEmpresa;
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
