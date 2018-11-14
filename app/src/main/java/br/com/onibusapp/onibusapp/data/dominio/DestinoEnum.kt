package br.com.onibusapp.onibusapp.data.dominio

/**
 * Created by diego on 26/07/2018.
 */

enum class DestinoEnum private constructor(var codigo: Int?, var sigla: String?) {

    IDA_VOLTA(0, "I/V"),
    IDA(1, "I"),
    VOLTA(2, "V");

    companion object {

        fun getSiglaByCodigo(codigo: Int?): String? {
            var sigla: String? = "I/V"
            for (destinoEnum in DestinoEnum.values()) {
                if (destinoEnum.codigo == codigo) {
                    sigla = destinoEnum.sigla
                    break
                }
            }
            return sigla
        }
    }
}
