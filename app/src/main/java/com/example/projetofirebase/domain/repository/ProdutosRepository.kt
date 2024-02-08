package com.example.projetofirebase.domain.repository

import android.app.Activity
import com.example.projetofirebase.data.model.Produtos

interface ProdutosRepository {

    suspend fun recuperarListaProdutos(activity: Activity, idCategoria: String): List<Produtos>

    suspend fun deletarProduto(activity: Activity, idCategoria: String, idProduto: String)

}