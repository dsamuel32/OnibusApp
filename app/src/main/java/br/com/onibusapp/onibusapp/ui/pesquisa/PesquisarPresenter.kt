package br.com.onibusapp.onibusapp.ui.pesquisa

import android.support.v4.app.FragmentManager
import android.util.Log
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO
import br.com.onibusapp.onibusapp.data.dao.LinhaDAO
import br.com.onibusapp.onibusapp.data.dominio.Favorito
import br.com.onibusapp.onibusapp.data.dominio.Linha
import br.com.onibusapp.onibusapp.ui.MapsFragment
import br.com.onibusapp.onibusapp.ui.pesquisa.PesquisarContract.Presenter.Companion.NENHUMA_LINHA_ENCONTRADA
import br.com.onibusapp.onibusapp.utils.Constantes
import br.com.onibusapp.onibusapp.utils.FragmentUtil

/**
 * Created by diego on 15/07/2018.
 */

class PesquisarPresenter(mPesquisarView: PesquisarContract.View,
                         linhaDAO: LinhaDAO,
                         favoritoDAO: FavoritoDAO,
                         fragmentManager: FragmentManager) : PesquisarContract.Presenter {

    private val mPesquisarView: PesquisarContract.View
    private val linhaDAO: LinhaDAO
    private val favoritoDAO: FavoritoDAO
    private val fragmentManager: FragmentManager
    private var linhas: List<Linha> = arrayListOf()

    init {
        this.mPesquisarView = kotlin.checkNotNull(mPesquisarView)
        this.linhaDAO = kotlin.checkNotNull(linhaDAO)
        this.favoritoDAO = kotlin.checkNotNull(favoritoDAO)
        this.fragmentManager = kotlin.checkNotNull(fragmentManager)
    }

    override fun selecionarEmpresa(position: Int?) {
        val codigoEmpresa = position!! + 1
        linhas = linhaDAO.findByCodigoEmpresa(codigoEmpresa)
        val nomeLinhas = montarListaNomeLinhas(linhas!!)
        mPesquisarView.atualizarSpinnerLinha(nomeLinhas)
    }

    private fun montarListaNomeLinhas(linhas: List<Linha>): List<String> {

        val nomes: MutableList<String> = linhas.map { linha -> linha.numero }.toMutableList()

        if (nomes.isEmpty()) {
            nomes.add(NENHUMA_LINHA_ENCONTRADA)
        }
        return nomes
    }

    override fun createDefaultAdapterLinha() {
        linhas = linhaDAO.findByCodigoEmpresa(1)
        val numeroLinhas = montarListaNomeLinhas(linhas!!)
        this.mPesquisarView.createDefaultAdapterLinha(numeroLinhas)
    }

    override fun pesquisar() {
        val filtro = mPesquisarView.selecionarFiltros()

        if (filtro.adicionarFavoritos!!) {
            val idLinha = recuperarIdLinha(filtro.linha)
            var favorito = Favorito(idLinha, filtro.sentido)
            favorito = favoritoDAO.salvar(favorito)
            Log.d("Favorito", favorito.toString())
        }
        Log.d("Filtros", filtro.toString())
        abrirMapa(filtro.linha, filtro.sentido, filtro.codigoEmprea)

    }

    private fun recuperarIdLinha(numero: String): Int? {
        val ids = linhas.filter { linha -> linha.numero == numero }.toList()

        return if (!ids.isEmpty()) ids[0].id
        else return null
    }

    private fun inicializaListaLinhas(): List<Linha> = linhaDAO.findAll()

    private fun abrirMapa(linha: String, sentido: Int?, codigoEmpresa: Int?) {
        FragmentUtil.getInstance(this.fragmentManager)
                .criarBundle()
                .parametros(Constantes.LINHA, linha)
                .parametros(Constantes.SENTIDO, sentido)
                .parametros(Constantes.CODIGO_EMPRESA, codigoEmpresa)
                .mudarTela(MapsFragment())
    }
}
