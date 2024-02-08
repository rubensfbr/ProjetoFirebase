package com.example.projetofirebase.data.repository

import android.app.Activity
import com.example.projetofirebase.data.model.Produtos
import com.example.projetofirebase.domain.repository.ProdutosRepository
import com.example.projetofirebase.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProdutosRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProdutosRepository {

    override suspend fun recuperarListaProdutos(
        activity: Activity, idCategoria: String
    ): List<Produtos> {
        val listaProdutos = mutableListOf<Produtos>()
        firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .get()
            .addOnFailureListener {
                activity.exibirMensagem(it.message!!)
            }
            .await().toObjects(Produtos::class.java).apply {
                listaProdutos.addAll(this)
            }
        return listaProdutos
    }

    override suspend fun deletarProduto(
        activity: Activity,
        idCategoria: String,
        idProduto: String
    ) {
        firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .document(idProduto)
            .delete()
            .addOnSuccessListener {
                activity.exibirMensagem("Produto removido com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao remover produto\n Erro = ${it.message}")
            }
        storage
            .getReference("fotos")
            .child("produtos")
            .child(idProduto)
            .delete()
            .addOnFailureListener {
                activity.exibirMensagem("Erro = ${it.message}")
            }
    }
}