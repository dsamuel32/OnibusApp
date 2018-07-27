package br.com.onibusapp.onibusapp.data.dominio;

/**
 * Created by diego on 26/07/2018.
 */

public enum DestinoEnum {

    IDA_VOLTA(0, "I/V"),
    IDA(1, "I"),
    VOLTA(2, "V");

    private Integer codigo;
    private String sigla;

    DestinoEnum(Integer codigo, String sigla) {
        this.codigo = codigo;
        this.sigla = sigla;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public static String getSiglaByCodigo(Integer codigo) {
        String sigla = "I/V";
        for (DestinoEnum destinoEnum : DestinoEnum.values()) {
            if (destinoEnum.codigo.equals(codigo)) {
                sigla = destinoEnum.sigla;
                break;
            }
        }
        return sigla;
    }
}
