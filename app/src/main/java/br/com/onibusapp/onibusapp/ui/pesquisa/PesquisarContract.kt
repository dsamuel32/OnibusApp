package br.com.onibusapp.onibusapp.ui.pesquisa

import br.com.onibusapp.onibusapp.data.dominio.Filtro

/**
 * Created by diego on 16/07/2018.
 */

interface PesquisarContract {

    interface View {

        fun atualizarSpinnerLinha(nomes: List<String>)
        fun createDefaultAdapterLinha(linhas: List<String>)
        fun selecionarFiltros(): Filtro

    }

    interface Presenter {

        fun selecionarEmpresa(position: Int?)
        fun createDefaultAdapterLinha()
        fun pesquisar()

        companion object {

            val NENHUMA_LINHA_ENCONTRADA = "Nenhuma linha encotrada"
        }

    }

}
