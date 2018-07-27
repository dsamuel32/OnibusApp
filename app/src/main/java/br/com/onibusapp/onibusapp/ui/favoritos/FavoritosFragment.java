package br.com.onibusapp.onibusapp.ui.favoritos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.onibusapp.onibusapp.dialog.ConfirmarDialog;
import br.com.onibusapp.onibusapp.R;
import br.com.onibusapp.onibusapp.adapter.FavoritoRecicleViewAdapter;
import br.com.onibusapp.onibusapp.adapter.RecyclerViewOnClickListener;
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO;
import br.com.onibusapp.onibusapp.data.dominio.Favorito;
import br.com.onibusapp.onibusapp.utils.StringUtils;

public class FavoritosFragment extends Fragment implements RecyclerViewOnClickListener, FavoritosContract.View {

    private RecyclerView recyclerView;
    private FavoritoRecicleViewAdapter favoritoRecicleViewAdapter;
    private FavoritosPresenter mFavoritosPresentar;
    private BottomNavigationView navigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_favoritos);
        this.mFavoritosPresentar = new FavoritosPresenter(this, new FavoritoDAO(getActivity()), this, getFragmentManager());
        navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        this.mFavoritosPresentar.carregarMapa(position);
        //navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
       StringUtils stringUtils = StringUtils.getInstance(getActivity());
        ConfirmarDialog confirmarDialog =
                new ConfirmarDialog(
                        stringUtils.getString(R.string.msg_deseja_salvar_audio),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFavoritosPresentar.apagar(position);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        confirmarDialog.setCancelable(false);
        confirmarDialog.show(getFragmentManager(), "dialogConfirmarAudio");

    }

    @Override
    public void carregar(List<Favorito> favoritos) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        favoritoRecicleViewAdapter = new FavoritoRecicleViewAdapter(getActivity(), favoritos);
        favoritoRecicleViewAdapter.setRecyclerViewOnClickListener(this);
        recyclerView.setAdapter(favoritoRecicleViewAdapter);

    }

    @Override
    public void atualizar(Integer posicao) {
        favoritoRecicleViewAdapter.removeItem(posicao);
    }

    @Override
    public void onDestroy() {
        this.mFavoritosPresentar = null;
        super.onDestroy();
    }
}
