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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.domain.Linha;


public class PesquisarFragment extends Fragment {

    private PesquisarPresenter pesquisarPresenter;
    private Spinner spEmpresa;
    private Spinner spLinha;
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
        cbxAddFavorititos = (CheckBox) view.findViewById(R.id.cbx_add_favoritos);
        btnPesquisar = (Button) view.findViewById(R.id.btn_pesquisar);
        linhas = inicializaListaLinhas();
        createAdapter(1);
        addEventos();
        return view;
    }

    private void addEventos() {
        spEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Selecionou", position + "");
                Integer empresaSelecionada = position + 1;
                List<String> nomes = filtrarLinhas(empresaSelecionada);
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
                linhaSelecionada = linhas.get(position);
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

    private List<Linha> inicializaListaLinhas() {
        List<Linha> linhas = new ArrayList<>();
        linhas.add(new Linha("0.006", 3));
        linhas.add(new Linha("512.1", 3));
        linhas.add(new Linha("0.512", 3));
        return linhas;
    }

    private void createAdapter(Integer codigoEmpresa) {

        List<String> nomes = filtrarLinhas(codigoEmpresa);

        linhaDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nomes);

        linhaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLinha.setAdapter(linhaDataAdapter);
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


}
