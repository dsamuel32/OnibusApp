package br.com.onibusapp.onibusapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.onibusapp.onibusapp.R
import br.com.onibusapp.onibusapp.data.dominio.Favorito

/**
 * Created by diego on 24/07/2018.
 */

class FavoritoRecicleViewAdapter(private val context: Context, private var favoritos: MutableList<Favorito>) : RecyclerView.Adapter<FavoritoViewHolder>() {

    private val layoutInflater: LayoutInflater
    private var recyclerViewOnClickListener: RecyclerViewOnClickListener? = null

    init {
        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoritoViewHolder {
        val view = this.layoutInflater.inflate(R.layout.item_favorito, viewGroup, false)
        return FavoritoViewHolder(view, recyclerViewOnClickListener)
    }

    override fun onBindViewHolder(viewHolder: FavoritoViewHolder, position: Int) {
        viewHolder.setFavorito(this.favoritos[position])
    }

    override fun getItemCount(): Int {
        return this.favoritos.size
    }

    fun setRecyclerViewOnClickListener(recyclerViewOnClickListener: RecyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener
    }

    fun updateData(favoritos: List<Favorito>) {
        this.favoritos.clear()
        this.favoritos.addAll(favoritos)
        notifyDataSetChanged()
    }

    fun addItem(position: Int, favorito: Favorito) {
        this.favoritos.add(position, favorito)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        this.favoritos.removeAt(position)
        notifyItemRemoved(position)
    }
}
