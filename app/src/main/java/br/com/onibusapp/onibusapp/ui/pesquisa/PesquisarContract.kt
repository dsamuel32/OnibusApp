package br.com.onibusapp.onibusapp.ui.pesquisa

import android.app.Activity
import android.widget.ProgressBar
import br.com.onibusapp.onibusapp.data.dominio.Empresa
import br.com.onibusapp.onibusapp.data.dominio.Filtro

/**
 * Created by diego on 16/07/2018.
 */

interface PesquisarContract {

    interface View {

        fun atualizarSpinnerLinha(nomes: MutableList<String>)
        fun atualizarSpinnerEmpresa(nomes: MutableList<String>)
        fun criarDefaultAdapterLinha(linhas: List<String>)
        fun selecionarFiltros(): Filtro
        fun criarDefaultAdapterEmpresa(empresas: List<String>)
        fun mostrarProgressBarCarregarEmpresas()
        fun fecharProgressBarCarregarEmpresas()
        fun exibirLayoutErroCarregarEmpresas()
        fun getCurrentActivity() : Activity
    }

    interface Presenter {

        fun selecionarEmpresa(position: Int)
        fun pesquisar()
        fun recuperarDadosFireBase()
        fun criarFiltrosAdapter()
        fun recuperarUrlEmpresa(empresa: String): String
        fun extrairNumeroLinha(linha: String): String

        companion object {

            val NENHUMA_LINHA_ENCONTRADA = "Nenhuma linha encotrada"
            val NENHUMA_EMPRESA_ENCONTRADA = "Nenhuma empresa encotrada"
        }

    }

}
