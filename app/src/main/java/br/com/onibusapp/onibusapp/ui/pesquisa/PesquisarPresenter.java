package br.com.onibusapp.onibusapp.ui.pesquisa;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        mPesquisarView.setPresenter(this);
    }

    @Override
    public void start() {
        linhas = inicializaListaLinhas();
    }

    @Override
    public List<String> filtrarLinhas(Integer codigoEmpresa) {
        List<String> nomes = linhas.stream().filter(linha -> linha.getCodigoEmpresa().equals(codigoEmpresa))
                .map(linha -> linha.getNumero())
                .collect(Collectors.toList());

        if (nomes.isEmpty()) {
            nomes.add("Nenhuma linha encotrada");
        }
        return nomes;
    }

    @Override
    public List<Linha> getLinhas() {
        return this.linhas;
    }

    @Override
    public Linha selecionarLinha(Integer position) {
        return this.linhas.get(position);
    }

    private List<Linha> inicializaListaLinhas() {
        List<Linha> linhas = new ArrayList<>();
        linhas.add(new Linha("0.006", 3));
        linhas.add(new Linha("512.1", 3));
        linhas.add(new Linha("0.512", 3));
        return linhas;
    }
}
