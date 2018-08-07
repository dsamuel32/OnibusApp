package br.com.onibusapp.onibusapp.ui.mapa;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.onibusapp.onibusapp.data.dominio.EmpresaEnum;
import br.com.onibusapp.onibusapp.domain.Onibus;
import br.com.onibusapp.onibusapp.utils.Constantes;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by diego on 02/08/2018.
 */

public class MapsPresenter implements MapsContract.Presenter {

    private final MapsContract.View mMapsView;
    private final FusedLocationProviderClient mFusedLocationClient;

    public MapsPresenter(@NonNull MapsContract.View mMapsView) {
        this.mMapsView = checkNotNull(mMapsView);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mMapsView.getCurrentActivity());
    }

    @Override
    public void mapaPronto() {
        this.mMapsView.mapaPronto();
    }

    @Override
    public void setLocalizacao(Double lat, Double lnt, Boolean focarLocalizacao) {
        this.mMapsView.setLocalizacao(lat, lnt, focarLocalizacao);
    }

    @Override
    public void getLocalizacaoUsuario(Boolean focarLocalizacao) {
        if (ActivityCompat.checkSelfPermission(mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMapsView.getCurrentActivity().requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, Constantes.PERMISSAO_LOCALIZACAO);
            return;
        }
        this.mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.mMapsView.getCurrentActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mMapsView.setLocalizacao(location.getLatitude(), location.getLongitude(), focarLocalizacao);
                        }
                    }
                });
    }

    @Override
    public void getLocalizacaoOnibus(String linha, Integer sentido, Integer codigoEmpresa) {
        RequestQueue queue = Volley.newRequestQueue(this.mMapsView.getCurrentActivity());
        String url = EmpresaEnum.getByCodigo(codigoEmpresa).getUrl();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("SUCESSO", "OK");
                        Map<String, Object> retMap = new Gson().fromJson(
                                response, new TypeToken<HashMap<String, Object>>() {}.getType()
                        );
                        List<List<String>> resposta = (List<List<String>>) retMap.get("Dados");
                        mMapsView.limparMapa();
                        getLocalizacaoUsuario(false);
                        for (List<String> dados : resposta) {

                            if (dados.get(5) != "") {
                                Onibus onibus = new Onibus();
                                onibus.setPrefixo(dados.get(0));
                                onibus.setDataHora(dados.get(1));
                                onibus.setLatitude(dados.get(2));
                                onibus.setLongitude(dados.get(3));
                                // onibus.setDirecao(Long.valueOf(dados.get(4)));
                                onibus.setLinha(dados.get(5));
                                onibus.setGtfsLinha(dados.get(6));
                                onibus.setSentido(dados.get(7));

                                if (linha.equals(onibus.getLinha())) {
                                    Log.d("MARCANDO", onibus.getLinha());
                                    LatLng localizacao = new LatLng(onibus.getLatitude(), onibus.getLongitude());
                                    mMapsView.addMarker(localizacao, onibus.getLinha(), onibus.getPrefixo());
                                }
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERRO", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }
}
