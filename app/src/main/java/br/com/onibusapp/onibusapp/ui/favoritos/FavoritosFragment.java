package br.com.onibusapp.onibusapp.ui.favoritos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.adapter.FavoritoRecicleViewAdapter;
import br.com.onibusapp.onibusapp.adapter.RecyclerViewOnClickListener;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;

public class FavoritosFragment extends Fragment implements RecyclerViewOnClickListener {

    private List<Favorito> favoritos;
    private RecyclerView recyclerView;
    private FavoritoRecicleViewAdapter favoritoRecicleViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        createRececleView(view);
        return view;
    }

    private void createRececleView(View view) {
        Favorito f = new Favorito();
        f.setNomeLinha("0.006");
        f.setCodigoSentido(1);

        Favorito f2 = new Favorito();
        f2.setNomeLinha("0.007");
        f2.setCodigoSentido(1);

        favoritos = new ArrayList<>();
        favoritos.add(f);
        favoritos.add(f2);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_favoritos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        favoritoRecicleViewAdapter = new FavoritoRecicleViewAdapter(getActivity(), favoritos);
        favoritoRecicleViewAdapter.setRecyclerViewOnClickListener(this);
        recyclerView.setAdapter(favoritoRecicleViewAdapter);
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
