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

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.ui.mapa.MapsContract;
import br.com.onibusapp.onibusapp.ui.mapa.MapsPresenter;

public class MapsFragment extends Fragment implements MapsContract.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Button btnAtualizar;
    private MapsContract.Presenter mMapsPresenter;

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
        btnAtualizar = (Button) view.findViewById(R.id.btnAtualizar);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        this.mMapsPresenter = new MapsPresenter(getActivity(), this);

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapsPresenter.getLocalizacaoOnibus();
            }
        });
        getParametros();
        return view;
    }

    private void getParametros() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String linha = bundle.getString("linha");
            Integer sentido = bundle.getInt("sentido");
            Log.d("PARAMENTROS", linha + " " + sentido);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
            case 1001: {
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
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1001);
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
}
