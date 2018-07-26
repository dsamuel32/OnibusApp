package br.com.onibusapp.onibusapp.ui.favoritos;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.onibusapp.onibusapp.adapter.RecyclerViewOnClickListener;
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by diego on 25/07/2018.
 */

public class FavoritosPresenter implements FavoritosContract.Presenter {

    private final FavoritoDAO favoritoDAO;
    private final FavoritosContract.View mFavoritosView;
    private List<Favorito> favoritos;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public FavoritosPresenter(@NonNull FavoritosContract.View mFavoritosView,
                              @NonNull FavoritoDAO favoritoDAO,
                              @NonNull RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.mFavoritosView = checkNotNull(mFavoritosView, "mFavoritosView cannot be null!");
        this.favoritoDAO = checkNotNull(favoritoDAO);
        this.recyclerViewOnClickListener = checkNotNull(recyclerViewOnClickListener);
        this.carregar();
    }

    private void carregar() {
        this.favoritos = this.favoritoDAO.findAll();
        mFavoritosView.carregar(this.favoritos);
    }

    @Override
    public void apagar(Integer posicao) {
        Favorito favorito = this.favoritos.get(posicao);
        this.favoritos.remove(posicao);
        this.favoritoDAO.apagar(favorito.getId());
        this.mFavoritosView.atualizar(posicao);
    }
}
