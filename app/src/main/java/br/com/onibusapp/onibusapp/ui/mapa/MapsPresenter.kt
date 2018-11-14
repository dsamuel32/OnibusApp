package br.com.onibusapp.onibusapp.ui.mapa

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.Log
import br.com.onibusapp.onibusapp.data.dominio.EmpresaEnum
import br.com.onibusapp.onibusapp.utils.Constantes
import br.com.onibusapp.onibusapp.utils.Conversor
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Created by diego on 02/08/2018.
 */

class MapsPresenter(mMapsView: MapsContract.View) : MapsContract.Presenter {

    private val mMapsView: MapsContract.View
    private val mFusedLocationClient: FusedLocationProviderClient

    init {
        this.mMapsView = kotlin.checkNotNull(mMapsView)
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mMapsView.getCurrentActivity())
    }

    override fun mapaPronto() {
        this.mMapsView.mapaPronto()
    }

    override fun setLocalizacao(lat: Double?, lnt: Double?, focarLocalizacao: Boolean?) {
        this.mMapsView.setLocalizacao(lat, lnt, focarLocalizacao)
    }

    override fun getLocalizacaoUsuario(focarLocalizacao: Boolean?) {
        if (ActivityCompat.checkSelfPermission(mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mMapsView.getCurrentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMapsView.getCurrentActivity().requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Constantes.PERMISSAO_LOCALIZACAO)
            return
        }
        this.mFusedLocationClient.lastLocation
                .addOnSuccessListener(this.mMapsView.getCurrentActivity()) { location ->
                    if (location != null) {
                        mMapsView.setLocalizacao(location.latitude, location.longitude, focarLocalizacao)
                    }
                }
    }

    override fun getLocalizacaoOnibus(linha: String, sentido: Int?, codigoEmpresa: Int?) {
        val queue = Volley.newRequestQueue(this.mMapsView.getCurrentActivity())
        val url = EmpresaEnum.getByCodigo(codigoEmpresa)!!.url
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener { response ->
                    // Display the first 500 characters of the response string.
                    Log.d("SUCESSO", "OK")
                    mMapsView.limparMapa()
                    getLocalizacaoUsuario(false)
                    Conversor().from(response)
                            .toListOnibus()
                            .filter { onibus -> linha == onibus.linha }
                            .forEach { onibus ->
                                Log.d("MARCANDO", onibus.linha)
                                val localizacao = LatLng(onibus.latitude, onibus.longitude)
                                mMapsView.addMarker(localizacao, onibus.linha, onibus.prefixo)
                            }
                }, Response.ErrorListener { error -> Log.e("ERRO", error.message) })

        queue.add(stringRequest)
    }
}
