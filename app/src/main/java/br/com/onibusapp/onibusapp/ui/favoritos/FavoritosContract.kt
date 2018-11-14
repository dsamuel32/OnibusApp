package br.com.onibusapp.onibusapp.ui.favoritos


import br.com.onibusapp.onibusapp.data.dominio.Favorito

/**
 * Created by diego on 16/07/2018.
 */

interface FavoritosContract {

    interface View {

        fun carregar(favoritos: List<Favorito>)

        fun atualizar(posicao: Int?)
    }

    interface Presenter {

        fun apagar(posicao: Int?)

        fun carregarMapa(posicao: Int?)
    }
}
