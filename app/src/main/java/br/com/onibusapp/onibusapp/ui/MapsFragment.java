package br.com.onibusapp.onibusapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
        mMapsPresenter.onMapReady();
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

    private boolean possuiPermissoes(Context context) {
        int accessFineLocation = PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int accessCourseLocation = PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocation == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },1);
            return true;
        }

        if (accessCourseLocation == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{ android.Manifest.permission.ACCESS_COARSE_LOCATION },1);
            return true;
        }

        return false;
    }

    @Override
    public void onMapReady() {
        if (possuiPermissoes(getActivity())) {
            try {
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.setMyLocationEnabled(true);
                this.mMapsPresenter.getMyLocation(true);
            } catch (SecurityException e) {
                Toast.makeText(getActivity(), "Erro", Toast.LENGTH_LONG);
            }
        }
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

/* EXEMPLO DE PERMISSOES RUNTIME
final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        insertDummyContact();
    }

*/
