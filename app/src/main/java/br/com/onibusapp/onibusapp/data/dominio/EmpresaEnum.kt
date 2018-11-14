package br.com.onibusapp.onibusapp.data.dominio

/**
 * Created by diego on 06/08/2018.
 */

enum class EmpresaEnum private constructor(var codigo: Int?, var url: String?) {

    MARECHAL(1, "http://200.250.197.43:13130/InLog/GetTempoReal"),
    PIONEIRA(2, "http://00078.transdatasmart.com.br:7801/ITS-InfoExport/api/Data/VeiculosGTFS"),
    PIRACICABANA(3, "http://00224.transdatasmart.com.br:22401/ITS-infoexport/api/Data/VeiculosGTFS"),
    SAO_JOSE(4, "http://00232.transdatasmart.com.br:23201/ITS-infoexport/api/Data/VeiculosGTFS");

    companion object {

        fun getByCodigo(codigo: Int?): EmpresaEnum? {
            var empresaEnum: EmpresaEnum? = null
            for (item in values()) {
                if (item.codigo == codigo) {
                    empresaEnum = item
                    break
                }
            }
            return empresaEnum
        }
    }
}
