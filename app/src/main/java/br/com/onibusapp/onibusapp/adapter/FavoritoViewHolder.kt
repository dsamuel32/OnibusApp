package br.com.onibusapp.onibusapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import br.com.onibusapp.onibusapp.R
import br.com.onibusapp.onibusapp.data.dominio.DestinoEnum
import br.com.onibusapp.onibusapp.data.dominio.Favorito

/**
 * Created by diego on 24/07/2018.
 */

class FavoritoViewHolder(itemView: View, private val recyclerViewOnClickListener: RecyclerViewOnClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    private val txtSentido: TextView
    private val txtLinha: TextView

    init {
        this.txtSentido = itemView.findViewById(R.id.txt_sentido)
        this.txtLinha = itemView.findViewById(R.id.txt_linha)
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onClick(v: View) {
        if (this.recyclerViewOnClickListener != null)
            this.recyclerViewOnClickListener.onClickListener(v, position)
    }

    override fun onLongClick(v: View): Boolean {
        if (this.recyclerViewOnClickListener != null)
            this.recyclerViewOnClickListener.onLongPressClickListener(v, layoutPosition)
        return false
    }

    fun setFavorito(favorito: Favorito) {
        txtSentido.text = DestinoEnum.getSiglaByCodigo(favorito.codigoSentido)
        txtLinha.text = favorito.nomeLinha
    }
}
