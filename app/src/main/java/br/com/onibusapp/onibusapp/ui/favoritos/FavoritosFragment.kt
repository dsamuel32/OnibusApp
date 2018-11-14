package br.com.onibusapp.onibusapp.ui.favoritos

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.onibusapp.onibusapp.R
import br.com.onibusapp.onibusapp.adapter.FavoritoRecicleViewAdapter
import br.com.onibusapp.onibusapp.adapter.RecyclerViewOnClickListener
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO
import br.com.onibusapp.onibusapp.data.dominio.Favorito
import br.com.onibusapp.onibusapp.dialog.ConfirmarDialog
import br.com.onibusapp.onibusapp.utils.StringUtils

class FavoritosFragment : Fragment(), RecyclerViewOnClickListener, FavoritosContract.View {

    private var recyclerView: RecyclerView? = null
    private var favoritoRecicleViewAdapter: FavoritoRecicleViewAdapter? = null
    private var mFavoritosPresentar: FavoritosPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_favoritos, container, false)
        recyclerView = view.findViewById<View>(R.id.rcv_favoritos) as RecyclerView
        this.mFavoritosPresentar = FavoritosPresenter(this, FavoritoDAO(activity), this, fragmentManager)
        return view
    }

    override fun onClickListener(view: View, position: Int) {
        this.mFavoritosPresentar!!.carregarMapa(position)
    }

    override fun onLongPressClickListener(view: View, position: Int) {
        val stringUtils = StringUtils.getInstance(activity)
        val confirmarDialog = ConfirmarDialog(
                stringUtils.getString(R.string.msg_deseja_salvar_audio),
                DialogInterface.OnClickListener { dialog, which -> mFavoritosPresentar!!.apagar(position) },
                DialogInterface.OnClickListener { dialog, which -> })
        confirmarDialog.isCancelable = false
        confirmarDialog.show(fragmentManager, "dialogConfirmarAudio")

    }

    override fun carregar(favoritos: MutableList<Favorito>) {

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        favoritoRecicleViewAdapter = FavoritoRecicleViewAdapter(activity, favoritos)
        favoritoRecicleViewAdapter!!.setRecyclerViewOnClickListener(this)
        recyclerView!!.adapter = favoritoRecicleViewAdapter

    }

    override fun atualizar(posicao: Int?) {
        favoritoRecicleViewAdapter!!.removeItem(posicao!!)
    }

    override fun onDestroy() {
        this.mFavoritosPresentar = null
        super.onDestroy()
    }
}
