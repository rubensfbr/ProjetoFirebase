package com.example.projetofirebase.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofirebase.R
import com.example.projetofirebase.data.adapter.CategoriaAdapter
import com.example.projetofirebase.data.model.Categoria
import com.example.projetofirebase.databinding.DialogLayoutBinding
import com.example.projetofirebase.databinding.FragmentMainBinding
import com.example.projetofirebase.presentation.viewmodels.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainFragmentViewModel>()

    private lateinit var recyclerView: RecyclerView
    private val adapter = CategoriaAdapter({ categoria -> onLongClick(categoria) },
        { categoria -> onClick(categoria) })

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        callRecyclerView()
        eventoClick()
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosIniciais()
    }

    override fun onResume() {
        super.onResume()

        observer()
    }

    override fun onStop() {
        super.onStop()

        job?.cancel()
    }

    private fun observer() {
        viewModel._categoria.observe(this) { listaCategoria ->
            adapter.setList(listaCategoria)
        }
    }

    private fun callRecyclerView() {
        recyclerView = binding.recyclerMain
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    private fun recuperarDadosIniciais() {
        viewModel.recuperarDados()
    }

    private fun eventoClick() {
        binding.buttonAdicionarCategoria.setOnClickListener {
            val bundle = bundleOf()
            findNavController().navigate(R.id.categoriaFragment, bundle)
        }
    }

    private fun onLongClick(categoria: Categoria) {
        val bindingDialog = DialogLayoutBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(bindingDialog.root)

        bindingDialog.textAtualizarNao.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.textRemoverNao.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.textRemoverSim.setOnClickListener {
            job = CoroutineScope(Dispatchers.IO).launch {
                viewModel.deletarCategoria(requireActivity(), categoria.id)
                recuperarDadosIniciais()
                dialog.dismiss()
            }
        }

        bindingDialog.textAtualizarSim.setOnClickListener {
            val bundle = bundleOf(
                "idCategoria" to categoria.id,
                "nome" to categoria.nome,
                "uri" to categoria.uri,
                "url" to categoria.imagem
            )

            findNavController().navigate(R.id.categoriaFragment, bundle)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun onClick(categoria: Categoria) {
        val bundle = bundleOf(
            "idCategoria" to categoria.id
        )
        findNavController().navigate(R.id.produtosFragment, bundle)

    }
}
