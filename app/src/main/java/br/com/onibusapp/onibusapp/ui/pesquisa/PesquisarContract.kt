package br.com.onibusapp.onibusapp.ui.pesquisa

import br.com.onibusapp.onibusapp.data.dominio.Empresa
import br.com.onibusapp.onibusapp.data.dominio.Filtro

/**
 * Created by diego on 16/07/2018.
 */

interface PesquisarContract {

    interface View {

        fun atualizarSpinnerLinha(nomes: MutableList<String>)
        fun criarDefaultAdapterLinha(linhas: List<String>)
        fun selecionarFiltros(): Filtro
        fun criarDefaultAdapterEmpresa(empresas: List<String>)

    }

    interface Presenter {

        fun selecionarEmpresa(position: Int)
        fun pesquisar()
        fun recuperarDadosFireBase()
        fun criarFiltrosAdapter()
        fun recuperarUrlEmpresa()

        companion object {

            val NENHUMA_LINHA_ENCONTRADA = "Nenhuma linha encotrada"
            val NENHUMA_EMPRESA_ENCONTRADA = "Nenhuma empresa encotrada"
        }

    }

}
