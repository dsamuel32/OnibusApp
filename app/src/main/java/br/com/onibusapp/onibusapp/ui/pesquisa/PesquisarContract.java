package br.com.onibusapp.onibusapp.ui.pesquisa;

import java.util.List;

import br.com.onibusapp.onibusapp.data.dominio.Filtro;

/**
 * Created by diego on 16/07/2018.
 */

public interface PesquisarContract {

    interface View {
        void atualizarSpinnerLinha(List<String> nomes);
        void createDefaultAdapterLinha(List<String> linhas);
        Filtro selecionarFiltros();
    }

    interface Presenter {

        String NENHUMA_LINHA_ENCONTRADA = "Nenhuma linha encotrada";

        void selecionarEmpresa(Integer position);

        void createDefaultAdapterLinha();

        void pesquisar();

    }

}
