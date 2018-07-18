package br.com.onibusapp.onibusapp.ui.pesquisa;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import br.com.onibusapp.onibusapp.domain.Linha;

import static com.google.common.base.Preconditions.checkNotNull;


public class PesquisarFragment extends Fragment implements PesquisarContract.View {

    private PesquisarContract.Presenter mPresenter;

    private Spinner spEmpresa;
    private Spinner spLinha;
    private Spinner spSentido;
    private CheckBox cbxAddFavorititos;
    private Button btnPesquisar;
    private List<Linha> linhas;

    private ArrayAdapter<String> linhaDataAdapter;

    private Linha linhaSelecionada;


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

        linhas = mPresenter.getLinhas();

        createDefaultAdapterLinha(1);
        addEventos();
        return view;
    }

    @Override
    public void setPresenter(@NonNull PesquisarContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private void addEventos() {
        spEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Selecionou", position + "");
                Integer empresaSelecionada = position + 1;
                List<String> nomes = mPresenter.filtrarLinhas(empresaSelecionada);
                linhaDataAdapter.clear();
                linhaDataAdapter.addAll(nomes);
                linhaDataAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spLinha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                linhaSelecionada = mPresenter.selecionarLinha(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createDefaultAdapterLinha(Integer codigoEmpresa) {

        List<String> nomes = mPresenter.filtrarLinhas(codigoEmpresa);
        linhaDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nomes);
        linhaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLinha.setAdapter(linhaDataAdapter);

    }

}
