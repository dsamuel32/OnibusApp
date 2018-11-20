package br.com.onibusapp.onibusapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.onibusapp.onibusapp.data.ScriptDB
import br.com.onibusapp.onibusapp.ui.MapsFragment
import br.com.onibusapp.onibusapp.ui.favoritos.FavoritosFragment
import br.com.onibusapp.onibusapp.ui.pesquisa.PesquisarFragment
import br.com.onibusapp.onibusapp.utils.FragmentUtil
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private var fragmentUtil: FragmentUtil? = null
    private var scriptDB: ScriptDB? = null


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentUtil!!.mudarTela(MapsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentUtil!!.mudarTela(PesquisarFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fragmentUtil!!.mudarTela(FavoritosFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        this.fragmentUtil = FragmentUtil.getInstance(supportFragmentManager)
        inicializaTelaMapa()
        scriptDB = ScriptDB(this)
        FirebaseApp.initializeApp(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun inicializaTelaMapa() {
        fragmentUtil!!.mudarTela(MapsFragment())
    }

}
