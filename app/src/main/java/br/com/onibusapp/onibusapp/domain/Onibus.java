package br.com.onibusapp.onibusapp.domain;

/**
 * Created by diego on 12/07/2018.
 */

public class Onibus {

    private String prefixo;
    private String dataHora;
    private Double latitude;
    private Double longitude;
    private Long direcao;
    private String linha;
    private String gtfsLinha;
    private String sentido;

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = Double.valueOf(latitude.replace(",", "."));
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = Double.valueOf(longitude.replace(",", "."));
    }

    public Long getDirecao() {
        return direcao;
    }

    public void setDirecao(Long direcao) {
        this.direcao = direcao;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getGtfsLinha() {
        return gtfsLinha;
    }

    public void setGtfsLinha(String gtfsLinha) {
        this.gtfsLinha = gtfsLinha;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }
}
