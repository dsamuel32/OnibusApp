package br.com.onibusapp.onibusapp.ui.pesquisa

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.onibusapp.onibusapp.R
import br.com.onibusapp.onibusapp.data.dao.FavoritoDAO
import br.com.onibusapp.onibusapp.data.dominio.Filtro
import com.google.firebase.database.FirebaseDatabase
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter


class PesquisarFragment : Fragment(), PesquisarContract.View {

    private lateinit var mPresenter: PesquisarContract.Presenter
    private var favoritoDAO: FavoritoDAO? = null

    private var spEmpresa: Spinner? = null
    private var spLinha: Spinner? = null
    private var cbxAddFavorititos: CheckBox? = null
    private var btnPesquisar: Button? = null
    private var autoCompleteTextView: AutoCompleteTextView? = null
    private lateinit var pb_carregar_empresa: ProgressBar
    private lateinit var relativeLayoutFormulario: RelativeLayout
    private lateinit var relativeLayoutErro: RelativeLayout

    private var linhaDataAdapter: ArrayAdapter<String>? = null
    private var empresaDataAdapter: ArrayAdapter<String>? = null



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_pesquisar, container, false)
        spEmpresa = view.findViewById<View>(R.id.sp_empresa) as Spinner
        //spLinha = view.findViewById<View>(R.id.sp_linha) as Spinner
        cbxAddFavorititos = view.findViewById<View>(R.id.cbx_add_favoritos) as CheckBox
        btnPesquisar = view.findViewById<View>(R.id.btn_pesquisar) as Button
        autoCompleteTextView = view.findViewById<View>(R.id.inp_linha) as AutoCompleteTextView
        pb_carregar_empresa = view.findViewById<View>(R.id.pb_carregar_empresa) as ProgressBar
        favoritoDAO = FavoritoDAO(activity)
        this.pb_carregar_empresa.visibility = ProgressBar.VISIBLE
        this.relativeLayoutFormulario = view.findViewById<View>(R.id.rl_formulario) as RelativeLayout
        this.relativeLayoutErro = view.findViewById<View>(R.id.rl_erro) as RelativeLayout
        this.relativeLayoutFormulario.visibility = RelativeLayout.INVISIBLE

        var fireBase = FirebaseDatabase.getInstance()
        if (fireBase.reference == null) fireBase.setPersistenceEnabled(true)

        mPresenter = PesquisarPresenter(this, favoritoDAO!!, fragmentManager, fireBase.reference)

        addEventos()
        return view
    }

    private fun addEventos() {
        addEventoSelecionarEmpresa()
        addPesquisarEventos()
    }

    private fun addEventoSelecionarEmpresa() {
        spEmpresa!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mPresenter.selecionarEmpresa(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun addPesquisarEventos() {
        btnPesquisar!!.setOnClickListener { mPresenter!!.pesquisar() }
    }


    override fun atualizarSpinnerLinha(nomes: MutableList<String>) {
        linhaDataAdapter!!.clear()
        linhaDataAdapter!!.addAll(nomes)
        linhaDataAdapter!!.notifyDataSetChanged()
    }

    override fun atualizarSpinnerEmpresa(nomes: MutableList<String>) {
        empresaDataAdapter!!.clear()
        empresaDataAdapter!!.addAll(nomes)
        empresaDataAdapter!!.notifyDataSetChanged()
    }

    override fun criarDefaultAdapterLinha(linhas: List<String>) {
        /*linhaDataAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, linhas)
        linhaDataAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spLinha!!.adapter = linhaDataAdapter*/

        linhaDataAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, linhas)
        autoCompleteTextView!!.setAdapter(linhaDataAdapter)
    }

    override fun criarDefaultAdapterEmpresa(empresas: List<String>) {
        empresaDataAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, empresas)
        empresaDataAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEmpresa!!.adapter = empresaDataAdapter
    }

    override fun selecionarFiltros(): Filtro {
        //val linha = spLinha!!.selectedItem.toString()
        val linha = autoCompleteTextView!!.text
        val empresa = spEmpresa!!.selectedItem.toString()
       /* if (linha == PesquisarContract.Presenter.NENHUMA_LINHA_ENCONTRADA) {

        }*/

        val adicionarFavoritos = cbxAddFavorititos!!.isChecked
        var url = this.mPresenter!!.recuperarUrlEmpresa(empresa)
        return Filtro(this.mPresenter.extrairNumeroLinha(linha.toString()), 0, adicionarFavoritos, url)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritoDAO = null
    }

    override fun onStart() {
        super.onStart()
        this.mPresenter.criarFiltrosAdapter()
    }

    override fun mostrarProgressBarCarregarEmpresas() {
        this.pb_carregar_empresa.visibility = ProgressBar.VISIBLE
    }

    override fun fecharProgressBarCarregarEmpresas() {
        if (this.pb_carregar_empresa.visibility == ProgressBar.VISIBLE)
            this.pb_carregar_empresa.visibility = ProgressBar.GONE
        this.relativeLayoutFormulario.visibility = RelativeLayout.VISIBLE

    }

    override fun exibirLayoutErroCarregarEmpresas() {
        this.relativeLayoutErro.visibility = RelativeLayout.VISIBLE
        this.relativeLayoutFormulario.visibility = RelativeLayout.GONE
    }

    override fun getCurrentActivity() : Activity {
        return activity
    }


}
