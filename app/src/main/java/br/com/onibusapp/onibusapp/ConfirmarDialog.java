package br.com.onibusapp.onibusapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by diego on 26/07/2018.
 */

public class ConfirmarDialog extends DialogFragment {

    private String mensagem;
    private OnClickListener onClickConfirm;
    private OnClickListener onClickNegative;

    public ConfirmarDialog() {
    }

    @SuppressLint("ValidFragment")
    public ConfirmarDialog(String mensagem, OnClickListener onClickConfirm, OnClickListener onClickNegative) {
        this.mensagem = mensagem;
        this.onClickConfirm = onClickConfirm;
        this.onClickNegative = onClickNegative;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mensagem).setPositiveButton("Ok", onClickConfirm)
                .setNegativeButton(R.string.btn_cancelar, onClickNegative)
                .setTitle(R.string.lb_confirmar);
        return builder.create();
    }

}
