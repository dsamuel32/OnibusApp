package br.com.onibusapp.onibusapp.ui.favoritos;


import java.util.List;

import br.com.onibusapp.onibusapp.data.dominio.Favorito;

/**
 * Created by diego on 16/07/2018.
 */

public interface FavoritosContract {

    interface View {

        void carregar(List<Favorito> favoritos);

        void atualizar(Integer posicao);
    }

    interface Presenter {

        void apagar(Integer posicao);

        void carregarMapa(Integer posicao);
    }
}
