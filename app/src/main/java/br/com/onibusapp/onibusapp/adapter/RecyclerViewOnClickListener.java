package br.com.onibusapp.onibusapp.adapter;

import android.view.View;

/**
 * Created by diego on 24/07/2018.
 */

public interface RecyclerViewOnClickListener {

    void onClickListener(View view, int position);

    void onLongPressClickListener(View view, int position);

}
