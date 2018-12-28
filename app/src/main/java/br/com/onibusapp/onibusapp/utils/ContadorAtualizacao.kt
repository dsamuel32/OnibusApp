package br.com.onibusapp.onibusapp.utils

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import java.util.concurrent.TimeUnit

class ContadorAtualizacao (var textView: TextView, var progressBar: ProgressBar, tempo: Long, tempoAtualizacao: Long) : CountDownTimer(tempo, tempoAtualizacao) {

    private var iniciado = false
    private var progresso = 20

    init {
        this.textView.visibility = View.VISIBLE
        this.progressBar.visibility = View.VISIBLE
        this.progressBar.progress = 20
        this.textView.text = "20"
    }

    override fun onTick(millisUntilFinished: Long) {
        val segundos = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
        textView.text = segundos.toString()
        progressBar.progress = --progresso
    }

    override fun onFinish() {
        cancel()
        this.textView.visibility = View.INVISIBLE
        this.progressBar.visibility = View.INVISIBLE
        this.progressBar.progress = 20
        this.progresso = 20
        this.iniciado = false
    }

    fun iniciar() {
        if (this.iniciado) {
            this.cancel()
            this.progresso = 20
        } else {
            this.iniciado = true
        }
        this.start()
    }

}