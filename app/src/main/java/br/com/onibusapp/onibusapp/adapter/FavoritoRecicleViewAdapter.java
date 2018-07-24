package br.com.onibusapp.onibusapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;

/**
 * Created by diego on 24/07/2018.
 */

public class FavoritoRecicleViewAdapter extends RecyclerView.Adapter<FavoritoViewHolder> {

    private Context context;
    private List<Favorito> favoritos;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public FavoritoRecicleViewAdapter(Context context, List<Favorito> favoritos) {
        this.context = context;
        this.favoritos = favoritos;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FavoritoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_favorito, viewGroup, false);
        return new FavoritoViewHolder(view, recyclerViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(FavoritoViewHolder audiAuidoViewHolder, int position) {
        audiAuidoViewHolder.setFavorito(this.favoritos.get(position));
    }

    @Override
    public int getItemCount() {
        return this.favoritos.size();
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }
}
