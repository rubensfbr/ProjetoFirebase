package com.example.projetofirebase.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.projetofirebase.domain.repository.AdicionarProdutoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdicionarProdutoViewModel @Inject constructor(private val repository: AdicionarProdutoRepository) :
    ViewModel() {

    suspend fun salvarProduto(
        activity: Activity, nome: String, valor: String, uri: String, idCategoria: String
    ): Boolean {
        return repository.salvarProduto(activity, nome, valor, uri, idCategoria)
    }

    suspend fun atualizarProdutoUri(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String
    ): Boolean {
        return repository.atualizarProdutoUri(
            activity, idCategoria, idProduto, nomeProduto,
            preco, uri
        )
    }

    suspend fun atualizarProdutoUrl(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String, url: String
    ): Boolean {
        return repository.atualizarProdutoUrl(
            activity, idCategoria, idProduto, nomeProduto,
            preco, uri, url
        )
    }
}