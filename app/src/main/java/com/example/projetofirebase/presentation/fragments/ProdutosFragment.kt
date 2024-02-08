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
import com.example.projetofirebase.data.adapter.ProdutosAdapter
import com.example.projetofirebase.data.model.Produtos
import com.example.projetofirebase.databinding.DialogLayoutBinding
import com.example.projetofirebase.databinding.FragmentProdutosBinding
import com.example.projetofirebase.presentation.viewmodels.ProdutosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProdutosFragment : Fragment(R.layout.fragment_produtos) {

    private lateinit var binding: FragmentProdutosBinding
    private val viewModel by viewModels<ProdutosViewModel>()

    private lateinit var recyclerView: RecyclerView
    private val adapter = ProdutosAdapter { produtos -> onLongClick(produtos) }
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProdutosBinding.bind(view)

        callRecyclerView()
        eventosClick()
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

    private fun recuperarDadosIniciais() {
        val idCategoria = requireArguments().getString("idCategoria")
        if (idCategoria != null) {
            viewModel.recuperarListaProdutos(requireActivity(), idCategoria)
        }
    }

    private fun eventosClick() {
        binding.buttonAdicionarProduto.setOnClickListener {
            val idCategoria = requireArguments().getString("idCategoria")
            val bundle = bundleOf(
                "idCategoria" to idCategoria
            )
            findNavController().navigate(R.id.adicionarProdutoFragment, bundle)
        }
    }


    private fun observer() {
        viewModel._produtos.observe(requireActivity()) {
            adapter.setList(it)
        }

    }

    private fun callRecyclerView() {
        recyclerView = binding.recyclerProdutos
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    private fun onLongClick(produtos: Produtos) {
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
                val idCategoria = requireArguments().getString("idCategoria")
                if (idCategoria != null) {
                    viewModel.deletarProdutos(requireActivity(), idCategoria, produtos.id)
                    viewModel.recuperarListaProdutos(requireActivity(), idCategoria)
                    dialog.dismiss()
                }
            }
        }

        bindingDialog.textAtualizarSim.setOnClickListener {
            val idCategoria = requireArguments().getString("idCategoria")
            if (idCategoria != null) {
                val bundle = bundleOf(
                    "idProduto" to produtos.id,
                    "nome" to produtos.nome,
                    "uri" to produtos.uri,
                    "preco" to produtos.preco,
                    "idCategoria" to idCategoria,
                    "url" to produtos.imagem
                )
                findNavController().navigate(R.id.adicionarProdutoFragment, bundle)
                dialog.dismiss()
            }

        }
        dialog.show()
    }
}