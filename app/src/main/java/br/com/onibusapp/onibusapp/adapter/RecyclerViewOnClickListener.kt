package br.com.onibusapp.onibusapp.adapter

import android.view.View

/**
 * Created by diego on 24/07/2018.
 */

interface RecyclerViewOnClickListener {

    fun onClickListener(view: View, position: Int)

    fun onLongPressClickListener(view: View, position: Int)

}
