package br.com.onibusapp.onibusapp.ui.pesquisa;

import java.util.List;

import br.com.onibusapp.onibusapp.BasePresenter;
import br.com.onibusapp.onibusapp.BaseView;
import br.com.onibusapp.onibusapp.domain.Linha;

/**
 * Created by diego on 16/07/2018.
 */

public interface PesquisarContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        List<String> filtrarLinhas(Integer codigoEmpresa);

        List<Linha> getLinhas();

        Linha selecionarLinha(Integer position);

    }

}
