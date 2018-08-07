package br.com.onibusapp.onibusapp.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.onibusapp.onibusapp.data.dominio.DFTransResposta;
import br.com.onibusapp.onibusapp.domain.Onibus;

/**
 * Created by diego on 06/08/2018.
 */

public final class Conversor {

    private Gson gson;
    private String resposta;

    public Conversor() {
        this.gson = new Gson();
    }

    public Conversor from(String resposta) {
        this.resposta = resposta;
        return this;
    }

    public List<Onibus> toListOnibus() {
        DFTransResposta dfTransResposta = this.gson.fromJson(this.resposta, DFTransResposta.class);
        List<Onibus> listaOnibus = new ArrayList<>();
        for (List<String> dados : dfTransResposta.getDados()) {

            if (dados.get(5) != "") {
                Onibus onibus = new Onibus();
                onibus.setPrefixo(dados.get(0));
                onibus.setDataHora(dados.get(1));
                onibus.setLatitude(dados.get(2));
                onibus.setLongitude(dados.get(3));
                // onibus.setDirecao(Long.valueOf(dados.get(4)));
                onibus.setLinha(dados.get(5));
                onibus.setGtfsLinha(dados.get(6));
                onibus.setSentido(dados.get(7));
                listaOnibus.add(onibus);
            }

        }
        return listaOnibus;
    }
}
