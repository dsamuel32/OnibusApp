package br.com.onibusapp.onibusapp.data.dominio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by diego on 12/07/2018.
 */

public class DFTransResposta implements Serializable {

    private List<String> Campos;
    private List<List<String>> Dados;

    public List<String> getCampos() {
        return Campos;
    }

    public void setCampos(List<String> campos) {
        this.Campos = campos;
    }

    public List<List<String>> getDados() {
        return Dados;
    }

    public void setDados(List<List<String>> dados) {
        this.Dados = dados;
    }
}
