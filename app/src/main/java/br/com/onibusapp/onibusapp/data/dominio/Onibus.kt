package br.com.onibusapp.onibusapp.data.dominio

data class Onibus (
        var prefixo: String,
        var dataHora: String,
        var latitude: Double,
        var longitude: Double,
        var direcao: Long,
        var linha: String,
        var gtfsLinha: String,
        var sentido: String)