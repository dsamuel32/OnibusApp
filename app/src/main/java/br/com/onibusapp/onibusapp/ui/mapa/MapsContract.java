package br.com.onibusapp.onibusapp.ui.mapa;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by diego on 02/08/2018.
 */

public interface MapsContract {

    interface View {

        void onMapReady();

    }

    interface Presenter {

        void onMapReady();

    }
}
