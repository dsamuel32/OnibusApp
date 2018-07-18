package br.com.onibusapp.onibusapp.ui.pesquisa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.List;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.domain.Filtro;


public class PesquisarFragment extends Fragment implements PesquisarContract.View {

    private PesquisarContract.Presenter mPresenter;

    private Spinner spEmpresa;
    private Spinner spLinha;
    private Spinner spSentido;
    private CheckBox cbxAddFavorititos;
    private Button btnPesquisar;

    private ArrayAdapter<String> linhaDataAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesquisar, container, false);
        spEmpresa = (Spinner) view.findViewById(R.id.sp_empresa);
        spLinha = (Spinner) view.findViewById(R.id.sp_linha);
        spSentido = (Spinner) view.findViewById(R.id.sp_sentido);
        cbxAddFavorititos = (CheckBox) view.findViewById(R.id.cbx_add_favoritos);
        btnPesquisar = (Button) view.findViewById(R.id.btn_pesquisar);
        mPresenter = new PesquisarPresenter(this);
        mPresenter.createDefaultAdapterLinha();

        addEventos();
        return view;
    }

    private void addEventos() {
        spEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.selecionarEmpresa(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pesquisar();
            }
        });
    }


    @Override
    public void atualizarSpinnerLinha(List<String> nomes) {
        linhaDataAdapter.clear();
        linhaDataAdapter.addAll(nomes);
        linhaDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void createDefaultAdapterLinha(List<String> linhas) {
        linhaDataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, linhas);
        linhaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLinha.setAdapter(linhaDataAdapter);
    }

    @Override
    public Filtro selecionarFiltros() {
        String linha = spLinha.getSelectedItem().toString();
        Integer sentido = spSentido.getSelectedItemPosition();
        Boolean adicionarFavoritos = cbxAddFavorititos.isChecked();
        return new Filtro(linha, sentido, adicionarFavoritos);
    }

}
