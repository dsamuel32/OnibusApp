package br.com.onibusapp.onibusapp.data;

/**
 * Created by diego on 18/07/2018.
 */

public enum DBEnum {

    SCRIPT_DATABASE_DELETE("DROP TABLE IF EXISTS TB_EMPRESA; DROP TABLE IF EXISTS TB_LINHA;"),
    NOME("ONIBUS_APP"),
    VERSAO("1");

    private String descricao;

    DBEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
