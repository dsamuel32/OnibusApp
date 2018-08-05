package br.com.onibusapp.onibusapp.ui.mapa;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by diego on 02/08/2018.
 */

public interface MapsContract {

    interface View {

        void onMapReady();

        void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao);

        Activity getCurrentActivity();

        void limparMapa();

        void addMarker(LatLng latLng, String linha, String prefixo);

    }

    interface Presenter {

        void onMapReady();

        void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao);

        void getMyLocation(Boolean focarLocalizacao);

        void getLocalizacaoOnibus();

    }
}
