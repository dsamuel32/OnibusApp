package br.com.onibusapp.onibusapp.ui.mapa;

import android.support.annotation.NonNull;

import br.com.onibusapp.onibusapp.ui.favoritos.FavoritosContract;

/**
 * Created by diego on 02/08/2018.
 */

public class MapsPresenter implements MapsContract.Presenter {

    private final MapsContract.View mMapsView;

    public MapsPresenter(@NonNull MapsContract.View mMapsView) {
        this.mMapsView = mMapsView;
    }


    @Override
    public void onMapReady() {

    }
}
