package br.com.onibusapp.onibusapp.ui.mapa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import br.com.onibusapp.onibusapp.ui.favoritos.FavoritosContract;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by diego on 02/08/2018.
 */

public class MapsPresenter implements MapsContract.Presenter {

    private final MapsContract.View mMapsView;
    private final FusedLocationProviderClient mFusedLocationClient;

    public MapsPresenter(@NonNull Activity activity, @NonNull MapsContract.View mMapsView) {
        this.mMapsView = checkNotNull(mMapsView);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mMapsView.getCurrentActivity());
    }


    @Override
    public void onMapReady() {
        this.mMapsView.onMapReady();
    }

    @Override
    public void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao) {
        this.mMapsView.setLocalizacao(lat, lnt, focarLocalizacao);
    }

    @Override
    public void getMyLocation(Boolean focarLocalizacao) {
        if (ActivityCompat.checkSelfPermission(this.mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.mMapsView.getCurrentActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mMapsView.setLocalizacao(location.getLatitude(), location.getLongitude(), false);
                        }
                    }
                });
    }
}
