package br.com.onibusapp.onibusapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.ui.mapa.MapsContract;
import br.com.onibusapp.onibusapp.ui.mapa.MapsPresenter;
import br.com.onibusapp.onibusapp.utils.Constantes;

public class MapsFragment extends Fragment implements MapsContract.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private MapsContract.Presenter mMapsPresenter;
    private Timer timer;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        MapView mapView = (MapView) view.findViewById(R.id.mapaFragment);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        this.mMapsPresenter = new MapsPresenter(this);
        getParametros();
        return view;
    }

    private void getParametros() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String linha = bundle.getString(Constantes.LINHA);
            Integer sentido = bundle.getInt(Constantes.SENTIDO);
            Integer codigoEmpresa = bundle.getInt(Constantes.CODIGO_EMPRESA);
            Log.d("PARAMENTROS", linha + " " + sentido + " " + codigoEmpresa);
           timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.d("Timer", "EXECUTOU");
                    mMapsPresenter.getLocalizacaoOnibus(linha, sentido, codigoEmpresa);
                }
            },0, TimeUnit.SECONDS.toMillis(30));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMapsPresenter.mapaPronto();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constantes.PERMISSAO_LOCALIZACAO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMapsPresenter.mapaPronto();
                }
                return;
            }
        }
    }

    @Override
    public void mapaPronto() {
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, Constantes.PERMISSAO_LOCALIZACAO);
            return;
        }
        mMap.setMyLocationEnabled(true);
        this.mMapsPresenter.getLocalizacaoUsuario(true);
    }

    @Override
    public void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao) {
        if (mMap != null) {
            LatLng minhaLocalizacao = new LatLng(lat, lnt);
            if (focarLocalizacao) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 14));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(minhaLocalizacao));
            }
        }
    }

    @Override
    public Activity getCurrentActivity() {
        return this.getActivity();
    }

    @Override
    public void limparMapa() {
        mMap.clear();
    }

    @Override
    public void addMarker(LatLng latLng, String linha, String prefixo) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(linha + " - " + prefixo));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
