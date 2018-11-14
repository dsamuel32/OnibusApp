package br.com.onibusapp.onibusapp.ui.mapa

import android.app.Activity

import com.google.android.gms.maps.model.LatLng

/**
 * Created by diego on 02/08/2018.
 */

interface MapsContract {

    interface View {

        fun getCurrentActivity() : Activity

        fun mapaPronto()

        fun setLocalizacao(lat: Double?, lnt: Double?, focarLocalizacao: Boolean?)

        fun limparMapa()

        fun addMarker(latLng: LatLng, linha: String, prefixo: String)

    }

    interface Presenter {

        fun mapaPronto()

        fun setLocalizacao(lat: Double?, lnt: Double?, focarLocalizacao: Boolean?)

        fun getLocalizacaoUsuario(focarLocalizacao: Boolean?)

        fun getLocalizacaoOnibus(linha: String, sentido: Int?, codigoEmpresa: Int?)

    }
}
