package br.com.onibusapp.onibusapp.ui.favoritos

import android.support.v4.app.FragmentManager
import br.com.onibusapp.onibusapp.adapter.RecyclerViewOnClickListener
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO
import br.com.onibusapp.onibusapp.data.dominio.Favorito
import br.com.onibusapp.onibusapp.ui.MapsFragment
import br.com.onibusapp.onibusapp.utils.Constantes
import br.com.onibusapp.onibusapp.utils.FragmentUtil

/**
 * Created by diego on 25/07/2018.
 */

class FavoritosPresenter(mFavoritosView: FavoritosContract.View,
                         favoritoDAO: FavoritoDAO,
                         recyclerViewOnClickListener: RecyclerViewOnClickListener,
                         private val fragmentManager: FragmentManager) : FavoritosContract.Presenter {

    private val favoritoDAO: FavoritoDAO
    private val mFavoritosView: FavoritosContract.View
    private var favoritos: MutableList<Favorito> = arrayListOf()
    private val recyclerViewOnClickListener: RecyclerViewOnClickListener

    init {
        this.mFavoritosView = kotlin.checkNotNull(mFavoritosView)
        this.favoritoDAO = kotlin.checkNotNull(favoritoDAO)
        this.recyclerViewOnClickListener = kotlin.checkNotNull(recyclerViewOnClickListener)
        this.carregar()
    }

    private fun carregar() {
        this.favoritos = this.favoritoDAO.findAll()
        mFavoritosView.carregar(this.favoritos!!)
    }

    override fun apagar(posicao: Int?) {
        val favorito = this.favoritos[posicao!!]
        //this.favoritos.removeAt(posicao)
        this.favoritoDAO.apagar(favorito.id)
        this.mFavoritosView.atualizar(posicao)
    }

    override fun carregarMapa(posicao: Int?) {
        val favorito = this.favoritos[posicao!!]
        FragmentUtil.getInstance(this.fragmentManager)
                .criarBundle()
                .parametros(Constantes.LINHA, favorito.linha)
                .parametros(Constantes.SENTIDO, favorito.codigoSentido)
                .parametros(Constantes.URL, favorito.url)
                .mudarTela(MapsFragment())
    }
}
