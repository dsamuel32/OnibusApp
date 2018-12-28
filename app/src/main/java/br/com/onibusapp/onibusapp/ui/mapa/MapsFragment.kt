package br.com.onibusapp.onibusapp.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import br.com.onibusapp.onibusapp.R
import br.com.onibusapp.onibusapp.ui.mapa.MapsContract
import br.com.onibusapp.onibusapp.ui.mapa.MapsPresenter
import br.com.onibusapp.onibusapp.utils.Constantes
import br.com.onibusapp.onibusapp.utils.ContadorAtualizacao
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import java.util.concurrent.TimeUnit

class MapsFragment : Fragment(), MapsContract.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var mMap: GoogleMap? = null
    private var tvLinha: TextView? = null
    private var tvTempoAtualizaca: TextView? = null
    private var progressBar: ProgressBar? = null
    private var mMapsPresenter: MapsContract.Presenter? = null
    private var timer: Timer? = null
    private var contadorAtualizacao: ContadorAtualizacao? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_maps, container, false)
        val mapView = view.findViewById<View>(R.id.mapaFragment) as MapView

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        val layoutDadosAtualizacao = view.findViewById<View>(R.id.dadosAtualizacao)
        layoutDadosAtualizacao.bringToFront()

        val layoutDadosLinha = view.findViewById<View>(R.id.dadosLinha)
        layoutDadosLinha.bringToFront()

        tvLinha = view.findViewById<View>(R.id.tv_linha) as TextView
        tvTempoAtualizaca = view.findViewById<View>(R.id.tv_tempo_atualizacao) as TextView
        progressBar = view.findViewById<View>(R.id.progress_atualizacao) as ProgressBar

        this.mMapsPresenter = MapsPresenter(this)
        getParametros()
        val navigation = activity.findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.menu.getItem(0).isChecked = true
        return view
    }

    private fun getParametros() {
        val bundle = this.arguments
        if (bundle != null) {
            val linha = bundle.getString(Constantes.LINHA)
            val sentido = bundle.getInt(Constantes.SENTIDO)
            val url = bundle.getString(Constantes.URL)
            Log.d("PARAMENTROS", "$linha $sentido $url")
            tvLinha!!.setText(linha)
            timer = Timer()
            contadorAtualizacao = ContadorAtualizacao(tvTempoAtualizaca!!, progressBar!!, 20000, 1000)
            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {

                    contadorAtualizacao!!.iniciar()

                    Log.d("Timer", "EXECUTOU")
                    mMapsPresenter!!.getLocalizacaoOnibus(linha, sentido, url)
                    //contadorAtualizacao!!.onFinish()
                    /*contadorAtualizacao = ContadorAtualizacao(tvTempoAtualizaca!!, 20000, 1000, true)
                    contadorAtualizacao!!.create()*/

                    //tempoAtualizacao();
                }
            }, 0, TimeUnit.SECONDS.toMillis(20))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMapsPresenter!!.mapaPronto()
    }

    override fun onConnected(bundle: Bundle?) {

    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constantes.PERMISSAO_LOCALIZACAO -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMapsPresenter!!.mapaPronto()
                }
                return
            }
        }
    }

    override fun mapaPronto() {
        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        mMap!!.uiSettings.isMapToolbarEnabled = true
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Constantes.PERMISSAO_LOCALIZACAO)
            return
        }
        mMap!!.isMyLocationEnabled = true
        this.mMapsPresenter!!.getLocalizacaoUsuario(true)
    }

    override fun setLocalizacao(lat: Double?, lnt: Double?, focarLocalizacao: Boolean?) {
        if (mMap != null) {
            val minhaLocalizacao = LatLng(lat!!, lnt!!)
            if (focarLocalizacao!!) {
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 14f))
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(minhaLocalizacao))
            }
        }
    }

    override fun getCurrentActivity(): Activity {
        return this.activity
    }



    override fun limparMapa() {
        mMap!!.clear()
    }

    override fun addMarker(latLng: LatLng, linha: String, prefixo: String) {
        mMap!!.addMarker(MarkerOptions().position(latLng).title("$linha - $prefixo")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_directions_bus_black_18dp))
    }

    override fun onDestroy() {
        super.onDestroy()
        this.limparMapa()

        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
        }
        mMapsPresenter = null

        if (contadorAtualizacao != null) {
            this.contadorAtualizacao!!.onFinish()
        }

    }

}
