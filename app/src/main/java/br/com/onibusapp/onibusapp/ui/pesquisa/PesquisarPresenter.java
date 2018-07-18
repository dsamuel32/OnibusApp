package br.com.onibusapp.onibusapp.ui.pesquisa;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.onibusapp.onibusapp.domain.Filtro;
import br.com.onibusapp.onibusapp.domain.Linha;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by diego on 15/07/2018.
 */

public class PesquisarPresenter implements PesquisarContract.Presenter {

    private final PesquisarContract.View mPesquisarView;
    private List<Linha> linhas;

    public PesquisarPresenter(@NonNull PesquisarContract.View mPesquisarView) {
        this.mPesquisarView = checkNotNull(mPesquisarView, "mPesquisarView cannot be null!");
        this.linhas = inicializaListaLinhas();
    }

    @Override
    public void selecionarEmpresa(Integer position) {
        Integer codigoEmpresa = position + 1;
        List<String> nomeLinhas = filtrarLinhas(codigoEmpresa);
        mPesquisarView.atualizarSpinnerLinha(nomeLinhas);
    }

    private List<String> filtrarLinhas(Integer codigoEmpresa) {
        List<String> nomes = linhas.stream().filter(linha -> linha.getCodigoEmpresa().equals(codigoEmpresa))
                .map(linha -> linha.getNumero())
                .collect(Collectors.toList());

        if (nomes.isEmpty()) {
            nomes.add("Nenhuma linha encotrada");
        }
        return nomes;
    }

    @Override
    public void createDefaultAdapterLinha() {
        List<String> linhas = filtrarLinhas(1);
        this.mPesquisarView.createDefaultAdapterLinha(linhas);
    }

    @Override
    public void pesquisar() {
        Filtro filtro = mPesquisarView.selecionarFiltros();
        Log.d("Filtros", filtro.toString());
    }

    private List<Linha> inicializaListaLinhas() {
        List<Linha> linhas = new ArrayList<>();
        linhas.add(new Linha("0.006", 3));
        linhas.add(new Linha("512.1", 3));
        linhas.add(new Linha("0.512", 3));
        return linhas;
    }
}
