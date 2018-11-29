package br.com.onibusapp.onibusapp.ui.pesquisa

import android.support.v4.app.FragmentManager
import android.util.Log
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO
import br.com.onibusapp.onibusapp.data.dominio.Empresa
import br.com.onibusapp.onibusapp.data.dominio.Empresas
import br.com.onibusapp.onibusapp.data.dominio.Favorito
import br.com.onibusapp.onibusapp.data.dominio.Linha
import br.com.onibusapp.onibusapp.ui.MapsFragment
import br.com.onibusapp.onibusapp.ui.pesquisa.PesquisarContract.Presenter.Companion.NENHUMA_EMPRESA_ENCONTRADA
import br.com.onibusapp.onibusapp.ui.pesquisa.PesquisarContract.Presenter.Companion.NENHUMA_LINHA_ENCONTRADA
import br.com.onibusapp.onibusapp.utils.Constantes
import br.com.onibusapp.onibusapp.utils.DataUtil
import br.com.onibusapp.onibusapp.utils.FragmentUtil
import br.com.onibusapp.onibusapp.utils.RedeUtil
import com.google.firebase.database.*

/**
 * Created by diego on 15/07/2018.
 */

class PesquisarPresenter(mPesquisarView: PesquisarContract.View,
                         favoritoDAO: FavoritoDAO,
                         fragmentManager: FragmentManager,
                         dataBaseReference: DatabaseReference) : PesquisarContract.Presenter {

    private val mPesquisarView: PesquisarContract.View
    private val favoritoDAO: FavoritoDAO
    private val fragmentManager: FragmentManager
    private var linhas: MutableList<Linha> = arrayListOf()
    private var empresas: MutableList<Empresa> = arrayListOf()
    private var mapaEmpresas: MutableMap<Int, MutableList<Linha>> = mutableMapOf()
    private var dataBaseReference: DatabaseReference

    init {
        this.mPesquisarView = kotlin.checkNotNull(mPesquisarView)
        this.favoritoDAO = kotlin.checkNotNull(favoritoDAO)
        this.fragmentManager = kotlin.checkNotNull(fragmentManager)
        this.dataBaseReference = kotlin.checkNotNull(dataBaseReference)

        val redeUtil = RedeUtil(mPesquisarView.getCurrentActivity())
        if (redeUtil.isNetworkConnected()) {
            this.mPesquisarView.mostrarProgressBarCarregarEmpresas()
            this.dataBaseReference.addValueEventListener(this.fireBaseListener())
        } else {
            this.mPesquisarView.fecharProgressBarCarregarEmpresas()
            this.mPesquisarView.exibirLayoutErroCarregarEmpresas()
        }

    }

    override fun recuperarDadosFireBase() {
        var fireBase = FirebaseDatabase.getInstance()
        fireBase.setPersistenceEnabled(true)
        this.dataBaseReference = fireBase.reference
        this.dataBaseReference.addValueEventListener(fireBaseListener())
    }

    fun fireBaseListener() : ValueEventListener{
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mPesquisarView.fecharProgressBarCarregarEmpresas()
                if (dataSnapshot.exists()) {
                    montarDadosFiltros(dataSnapshot)
                    var nomesEmpresas = montarListaNomeEmprasas(empresas)
                    mPesquisarView.atualizarSpinnerEmpresa(nomesEmpresas)
                    selecionarEmpresa(0)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERRO", "Erro recuperar dados Firebase")
                mPesquisarView.fecharProgressBarCarregarEmpresas()
                mPesquisarView.exibirLayoutErroCarregarEmpresas()
            }
        }

        return listener
    }

    fun montarDadosFiltros(dataSnapshot: DataSnapshot) {
        val data = dataSnapshot.getValue(Empresas::class.java)

        if (data != null) {
            var i = 0
            data.empresas.forEach {
                it -> empresas.add(Empresa(it.nome, it.url))
                mapaEmpresas.put(i, it.linhas)
                i++
            }
        }

    }

    fun removerFireBaseListener() {
        this.dataBaseReference.removeEventListener(fireBaseListener())
    }

    override fun selecionarEmpresa(position: Int) {
        if (this.mapaEmpresas.isNotEmpty()) {
            this.linhas = this.mapaEmpresas.get(position)!!
            val nomeLinhas = this.montarListaNomeLinhas(this.linhas)
            this.mPesquisarView.atualizarSpinnerLinha(nomeLinhas)
        }
    }

    override fun criarFiltrosAdapter() {
        this.criarDefaultLinhaEmpresa()
        this.criarDefaultAdapterLinha()
    }

    fun criarDefaultLinhaEmpresa() {
        val nomesEmpresas = this.montarListaNomeEmprasas(this.empresas)
        this.mPesquisarView.criarDefaultAdapterEmpresa(nomesEmpresas)
    }

    private fun montarListaNomeEmprasas(empresas: MutableList<Empresa>): MutableList<String> {

        var nomes: MutableList<String> = empresas.map { it -> it.nome }.toMutableList()

        if (nomes == null) nomes = arrayListOf()

        if (nomes.isEmpty()) {
            nomes.add(NENHUMA_EMPRESA_ENCONTRADA)
        }
        return nomes
    }

    fun criarDefaultAdapterLinha() {
        //this.linhas = this.mapaEmpresas.get(0)!!
        val numeroLinhas = montarListaNomeLinhas(linhas)
        this.mPesquisarView.criarDefaultAdapterLinha(numeroLinhas)
    }

    private fun montarListaNomeLinhas(linhas: List<Linha>): MutableList<String> {
        val dataUtil = DataUtil()
        val diaSemana = dataUtil.recuperarDiaSemana()
        var linhasDiaSemana: MutableList<Linha> = linhas.filter { linha -> linha.dias.equals(diaSemana) }.toMutableList()
        var nomes: MutableList<String> = linhasDiaSemana.map { linha -> "${linha.linha} - ${linha.nome}" }.toMutableList()

        if (nomes == null) nomes = arrayListOf()

        if (nomes.isEmpty()) {
            nomes.add(NENHUMA_LINHA_ENCONTRADA)
        }
        return nomes
    }

    override fun pesquisar() {
        val filtro = mPesquisarView.selecionarFiltros()

        if (filtro.adicionarFavoritos!!) {
            var favorito = Favorito(null, filtro.sentido, filtro.linha, filtro.url)
            favorito = favoritoDAO.salvar(favorito)
            Log.d("Favorito", favorito.toString())
        }
        Log.d("Filtros", filtro.toString())
        abrirMapa(filtro.linha, filtro.sentido, filtro.url)

    }

    private fun recuperarNumeroLinha(numero: String): Int? {
        val ids = linhas.filter { linha -> linha.linha == numero }.toList()

        return if (!ids.isEmpty()) ids[0].id
        else return null
    }

    private fun abrirMapa(linha: String, sentido: Int, url: String) {
        FragmentUtil.getInstance(this.fragmentManager)
                .criarBundle()
                .parametros(Constantes.LINHA, linha)
                .parametros(Constantes.SENTIDO, sentido)
                .parametros(Constantes.URL, url)
                .mudarTela(MapsFragment())
    }

    override fun recuperarUrlEmpresa(nomeEmpresa: String): String {
        val empresa = this.empresas.filter { it -> it.nome.equals(nomeEmpresa) }[0]
        return empresa.url
    }

    override fun extrairNumeroLinha(linha: String): String {
        val dados = linha.split("-")
        return dados[0].trim()
    }
}
