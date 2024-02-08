package com.example.projetofirebase.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projetofirebase.R
import com.example.projetofirebase.databinding.FragmentAdicionarProdutoBinding
import com.example.projetofirebase.presentation.helper.Permissoes
import com.example.projetofirebase.presentation.viewmodels.AdicionarProdutoViewModel
import com.example.projetofirebase.utils.exibirMensagem
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AdicionarProdutoFragment : Fragment(R.layout.fragment_adicionar_produto) {

    private lateinit var binding: FragmentAdicionarProdutoBinding
    private val viewModel by viewModels<AdicionarProdutoViewModel>()

    private lateinit var nome: String
    private lateinit var preco: String
    private lateinit var imagemUri: String
    private lateinit var imagemUrl: String
    private var job: Job? = null

    private val gerencidorGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            Picasso.get()
                .load(uri)
                .into(binding.imageProduto)
            imagemUri = uri.toString()
        } else {
            activity?.exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdicionarProdutoBinding.bind(view)

        recuperarDados()
        eventosClick()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    private fun recuperarDados() {
        val nomeProduto = requireArguments().getString("nome")
        val uri = requireArguments().getString("uri")
        val preco = requireArguments().getString("preco")
        val url = requireArguments().getString("url")
        if (nomeProduto != null && uri != null && preco != null && url != null) {
            binding.textNomeProduto.setText(nomeProduto)
            binding.textValorProduto.setText(preco)
            imagemUri = uri
            imagemUrl = url
            Picasso.get()
                .load(imagemUrl)
                .into(binding.imageProduto)
        }
    }

    private fun eventosClick() {

        binding.imagegaleriaProduto.setOnClickListener {
            Permissoes.verificarPermissoes(requireActivity())
            gerencidorGaleria.launch("image/*")
        }

        binding.buttonAdicionarProduto.setOnClickListener {
            if (validacao()) {
                job = CoroutineScope(Dispatchers.IO).launch {
                    val idProduto = requireArguments().getString("idProduto")
                    if (idProduto == null) {
                        salvarProduto()
                    } else {
                        atualizarProduto(idProduto)
                    }
                }
            }
        }
    }

    private suspend fun salvarProduto() {
        val idCategoria = requireArguments().getString("idCategoria")
        val retorno = viewModel.salvarProduto(
            requireActivity(), nome, preco, imagemUri, idCategoria!!
        )
        if (!retorno) {
            withContext(Dispatchers.Main) {
                val bundle = bundleOf("idCategoria" to idCategoria)
                findNavController().navigate(R.id.produtosFragment, bundle)
            }
        }
    }

    private suspend fun atualizarProduto(idProduto: String) {
        val idCategoria = requireArguments().getString("idCategoria")
        val uri = requireArguments().getString("uri")
        if (uri != imagemUri) {
            val retorno = viewModel.atualizarProdutoUri(
                requireActivity(), idCategoria!!, idProduto, nome, preco, imagemUri
            )
            if (!retorno) {
                withContext(Dispatchers.Main) {
                    val bundle = bundleOf("idCategoria" to idCategoria)
                    findNavController().navigate(R.id.produtosFragment, bundle)
                    findNavController().popBackStack()
                }
            }
        } else {
            val retorno = viewModel.atualizarProdutoUrl(
                requireActivity(), idCategoria!!, idProduto, nome, preco, imagemUri, imagemUrl
            )
            if (!retorno) {
                withContext(Dispatchers.Main) {
                    val bundle = bundleOf("idCategoria" to idCategoria)
                    findNavController().navigate(R.id.produtosFragment, bundle)
                }
            }
        }
    }

    private fun validacao(): Boolean {
        nome = binding.textNomeProduto.text.toString().trim()
        preco = binding.textValorProduto.text.toString().trim()
        if (nome.isNotEmpty()) {
            binding.textInputNomeProduto.error = null
            if (preco.isNotEmpty()) {
                binding.textInputValor.error = null
                if (imagemUri != null) {
                    return true
                } else {
                    activity?.exibirMensagem("Nenhuma imagem selecionada")
                    return false
                }
            } else {
                binding.textInputValor.error = "Preencher valor do produto"
                return false
            }
        } else {
            binding.textInputNomeProduto.error = "Preencher nome do produto"
            return false
        }
    }
}