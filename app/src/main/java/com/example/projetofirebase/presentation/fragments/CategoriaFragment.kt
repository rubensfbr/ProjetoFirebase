package com.example.projetofirebase.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projetofirebase.R
import com.example.projetofirebase.databinding.FragmentCategoriaBinding
import com.example.projetofirebase.presentation.helper.Permissoes
import com.example.projetofirebase.presentation.viewmodels.CategoriaViewModel
import com.example.projetofirebase.utils.exibirMensagem
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CategoriaFragment : Fragment(R.layout.fragment_categoria) {

    private lateinit var binding: FragmentCategoriaBinding
    private val viewModel by viewModels<CategoriaViewModel>()
    private var job: Job? = null

    private lateinit var categoria: String
    private lateinit var imagemUri: String
    private lateinit var imagemUrl: String

    init {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    private val gerenciadorGaleria = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            Picasso.get()
                .load(uri)
                .into(binding.imagemCategoria)
            imagemUri = uri.toString()
        } else {
            activity?.exibirMensagem("Nenhuma imagem selecionada")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriaBinding.bind(view)

        verificarPermissoes()
        recuperarDados()
        eventoClick()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    private fun verificarPermissoes() {
        Permissoes.verificarPermissoes(requireActivity())
    }


    private fun recuperarDados() {
        val nome = requireArguments().getString("nome")
        val uri = requireArguments().getString("uri")
        val url = requireArguments().getString("url")
        if (nome != null && uri != null && url != null) {
            binding.textCategoria.setText(nome)
            imagemUri = uri
            imagemUrl = url
            Picasso.get()
                .load(imagemUrl)
                .into(binding.imagemCategoria)
        }
    }

    private fun eventoClick() {

        binding.imageGaleria.setOnClickListener {
            gerenciadorGaleria.launch(arrayOf("image/*"))
        }

        binding.buttonAdicionar.setOnClickListener {
            if (validacao()) {
                job = CoroutineScope(Dispatchers.IO).launch {
                    val idCategoria = requireArguments().getString("idCategoria")
                    if (idCategoria == null) {
                        salvarCategoria()
                    } else {
                        atualizarCategoria(idCategoria)
                    }
                }
            }
        }
    }

    private suspend fun salvarCategoria() {
        val retorno =
            viewModel.salvarCategoria(requireActivity(), categoria, imagemUri)
        if (!retorno) {
            withContext(Dispatchers.Main) {
                requireActivity().finish()
            }
        }
    }

    private suspend fun atualizarCategoria(id: String) {
        val uri = requireArguments().getString("uri")?.toUri().toString()
        if (uri != imagemUri) {
            val retorno =
                viewModel.atualizarCategoriaUri(
                    requireActivity(), id, categoria, imagemUri
                )
            if (!retorno) {
                withContext(Dispatchers.Main) {
                    requireActivity().finish()
                }
            }
        } else {
            val retorno =
                viewModel.atualizarCategoriaUrl(
                    requireActivity(), id, categoria, uri, imagemUrl
                )
            if (!retorno) {
                withContext(Dispatchers.Main) {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun validacao(): Boolean {
        categoria = binding.textCategoria.text.toString().trim()
        if (categoria.isNotEmpty()) {
            binding.textInputCategoria.error = null

            if (imagemUri.isNotEmpty()) {
                return true
            } else {
                activity?.exibirMensagem("Nenhuma imagem selecionada")
                return false
            }
        } else {
            binding.textInputCategoria.error = "Preencher campo"
            return false
        }
    }
}