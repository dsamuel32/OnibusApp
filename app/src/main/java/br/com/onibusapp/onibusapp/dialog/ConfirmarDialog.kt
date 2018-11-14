package br.com.onibusapp.onibusapp.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import br.com.onibusapp.onibusapp.R

/**
 * Created by diego on 26/07/2018.
 */

@SuppressLint("ValidFragment")
class ConfirmarDialog (private val mensagem: String,
                       private val onClickConfirm: OnClickListener,
                       private val onClickNegative: OnClickListener) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(this.mensagem).setPositiveButton("Ok", this.onClickConfirm)
                .setNegativeButton(R.string.btn_cancelar, this.onClickNegative)
                .setTitle(R.string.lb_confirmar)
        return builder.create()
    }

}
