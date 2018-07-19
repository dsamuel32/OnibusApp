package br.com.onibusapp.onibusapp.ui.pesquisa;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO;
import br.com.onibusapp.onibusapp.data.dao.LinhaDAO;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;
import br.com.onibusapp.onibusapp.data.dominio.Filtro;
import br.com.onibusapp.onibusapp.data.dominio.Linha;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by diego on 15/07/2018.
 */

public class PesquisarPresenter implements PesquisarContract.Presenter {

    private final PesquisarContract.View mPesquisarView;
    private final LinhaDAO linhaDAO;
    private final FavoritoDAO favoritoDAO;
    private List<Linha> linhas;

    public PesquisarPresenter(@NonNull PesquisarContract.View mPesquisarView,
                              @NonNull LinhaDAO linhaDAO,
                              @NonNull FavoritoDAO favoritoDAO) {
        this.mPesquisarView = checkNotNull(mPesquisarView, "mPesquisarView cannot be null!");
        this.linhaDAO = checkNotNull(linhaDAO);
        this.favoritoDAO = checkNotNull(favoritoDAO);
    }

    @Override
    public void selecionarEmpresa(Integer position) {
        Integer codigoEmpresa = position + 1;
        linhas = linhaDAO.findByCodigoEmpresa(codigoEmpresa);
        List<String> nomeLinhas = montarListaNomeLinhas(linhas);
        mPesquisarView.atualizarSpinnerLinha(nomeLinhas);
    }

    private List<String> montarListaNomeLinhas(List<Linha> linhas) {
        List<String> nomes = linhas.stream().map(linha -> linha.getNumero())
                .collect(Collectors.toList());

        if (nomes.isEmpty()) {
            nomes.add(NENHUMA_LINHA_ENCONTRADA);
        }
        return nomes;
    }

    @Override
    public void createDefaultAdapterLinha() {
        linhas = linhaDAO.findByCodigoEmpresa(1);
        List<String> numeroLinhas = montarListaNomeLinhas(linhas);
        this.mPesquisarView.createDefaultAdapterLinha(numeroLinhas);
    }

    @Override
    public void pesquisar() {
        Filtro filtro = mPesquisarView.selecionarFiltros();

        if (filtro.getAdicionarFavoritos()) {
            Integer idLinha = recuperarIdLinha(filtro.getLinha());
            Favorito favorito = new Favorito(idLinha, filtro.getSentido());
            favorito = favoritoDAO.salvar(favorito);
            Log.d("Favorito", favorito.toString());
        }

        Log.d("Filtros", filtro.toString());
    }

    private Integer recuperarIdLinha(String numero) {
        List<Integer> ids = linhas.stream().filter(linha -> linha.getNumero().equals(numero))
                              .map(linha ->  linha.getId())
                              .collect(Collectors.toList());

        if (!ids.isEmpty()) {
            return ids.get(0);
        }
        return null;
    }

    private List<Linha> inicializaListaLinhas() {
        return linhaDAO.findAll();
    }
}
