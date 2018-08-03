package br.com.onibusapp.onibusapp.ui.mapa;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by diego on 02/08/2018.
 */

public interface MapsContract {

    interface View {

        void onMapReady();

        void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao);

        Activity getCurrentActivity();

    }

    interface Presenter {

        void onMapReady();

        void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao);

        void getMyLocation(Boolean focarLocalizacao);

    }
}
