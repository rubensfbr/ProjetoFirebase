package com.example.projetofirebase.domain.repository

import android.app.Activity

interface AdicionarProdutoRepository {

    suspend fun salvarProduto(
        activity: Activity, nome: String, valor: String, uri: String, idCategoria: String
    ): Boolean

    suspend fun atualizarProdutoUri(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String
    ): Boolean

    suspend fun atualizarProdutoUrl(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String, url: String
    ): Boolean
}