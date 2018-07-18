package br.com.onibusapp.onibusapp.ui.pesquisa;

import java.util.List;

import br.com.onibusapp.onibusapp.domain.Filtro;

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

        void selecionarEmpresa(Integer position);

        void createDefaultAdapterLinha();

        void pesquisar();

    }

}
