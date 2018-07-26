package br.com.onibusapp.onibusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;

/**
 * Created by diego on 24/07/2018.
 */

public class FavoritoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private RecyclerViewOnClickListener recyclerViewOnClickListener;
    private TextView txtSentido;
    private TextView txtLinha;

    public FavoritoViewHolder(View itemView, RecyclerViewOnClickListener recyclerViewOnClickListener) {
        super(itemView);
        this.txtSentido = itemView.findViewById(R.id.txt_sentido);
        this.txtLinha = itemView.findViewById(R.id.txt_linha);
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (this.recyclerViewOnClickListener != null)
            this.recyclerViewOnClickListener.onClickListener(v, getPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if (this.recyclerViewOnClickListener != null)
            this.recyclerViewOnClickListener.onLongPressClickListener(v, getLayoutPosition());
        return false;
    }

    public void setFavorito(Favorito favorito) {
        txtSentido.setText(favorito.getCodigoSentido().toString());
        txtLinha.setText(favorito.getNomeLinha());
    }
}
