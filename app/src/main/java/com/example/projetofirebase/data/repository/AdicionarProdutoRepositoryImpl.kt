package com.example.projetofirebase.data.repository

import android.app.Activity
import android.net.Uri
import androidx.core.net.toUri
import com.example.projetofirebase.domain.repository.AdicionarProdutoRepository
import com.example.projetofirebase.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdicionarProdutoRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AdicionarProdutoRepository {

    override suspend fun salvarProduto(
        activity: Activity, nome: String, valor: String, uri: String, idCategoria: String
    ): Boolean {
        val imagemUri = uri.toUri()
        var url: Uri
        val idProduto = firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .document().id

        storage
            .getReference("fotos")
            .child("produtos")
            .child(idProduto)
            .putFile(imagemUri)
            .await().storage.downloadUrl
            .await().apply {
                url = this
            }
        val produto = mapOf(
            "imagem" to url.toString(),
            "nome" to nome,
            "preco" to valor,
            "id" to idProduto,
            "uri" to uri
        )
        return firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .document(idProduto)
            .set(produto)
            .addOnSuccessListener {
                activity.exibirMensagem("Produto adicionado com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao adicionar produto\nErro = ${it.message}")
            }.isSuccessful
    }

    override suspend fun atualizarProdutoUri(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String
    ): Boolean {

        val imagemUri = uri.toUri()
        var url: Uri
        storage
            .getReference("fotos")
            .child("produtos")
            .child(idProduto)
            .putFile(imagemUri)
            .await().storage.downloadUrl
            .await().apply {
                url = this
            }
        val produto = mapOf(
            "imagem" to url.toString(),
            "nome" to nomeProduto,
            "id" to idProduto,
            "uri" to uri.toString(),
            "preco" to preco
        )
        return firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .document(idProduto)
            .set(produto)
            .addOnSuccessListener {
                activity.exibirMensagem("produto atualizada com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao atualizar produto")
            }.isSuccessful
    }

    override suspend fun atualizarProdutoUrl(
        activity: Activity, idCategoria: String, idProduto: String, nomeProduto: String,
        preco: String, uri: String, url: String
    ): Boolean {

        val produto = mapOf(
            "imagem" to url,
            "nome" to nomeProduto,
            "id" to idProduto,
            "uri" to uri,
            "preco" to preco
        )
        return firestore
            .collection("produtos")
            .document(idCategoria)
            .collection("itens")
            .document(idProduto)
            .set(produto)
            .addOnSuccessListener {
                activity.exibirMensagem("produto atualizada com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao atualizar produto")
            }.isSuccessful
    }
}
