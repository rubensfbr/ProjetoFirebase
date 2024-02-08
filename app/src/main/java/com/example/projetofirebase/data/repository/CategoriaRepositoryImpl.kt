package com.example.projetofirebase.data.repository

import android.app.Activity
import android.net.Uri
import androidx.core.net.toUri
import com.example.projetofirebase.domain.repository.CategoriaRepository
import com.example.projetofirebase.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriaRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : CategoriaRepository {

    override suspend fun salvarCategoria(activity: Activity, categoria: String, uri: String)
            : Boolean {
        val uri = uri.toUri()
        var url: Uri
        val id = firestore.collection("categorias").document().id
        storage
            .getReference("fotos")
            .child("categorias")
            .child(id)
            .putFile(uri)
            .await().storage.downloadUrl
            .await().apply {
                url = this
            }
        val dados = mapOf(
            "imagem" to url.toString(),
            "nome" to categoria,
            "id" to id,
            "uri" to uri.toString()
        )
        return firestore
            .collection("categorias")
            .document(id)
            .set(dados)
            .addOnSuccessListener {
                activity.exibirMensagem("Categoria inserida com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao inserir categoria")
            }.isSuccessful
    }

    override suspend fun atualizarCategoriaUri(
        activity: Activity, id: String, categoria: String, uri: String
    ): Boolean {
        val uri = uri.toUri()
        var url: Uri
        storage
            .getReference("fotos")
            .child("categorias")
            .child(id)
            .putFile(uri)
            .await().storage.downloadUrl
            .await().apply {
                url = this
            }
        val dados = mapOf(
            "imagem" to url.toString(),
            "nome" to categoria,
            "id" to id,
            "uri" to uri.toString()
        )
        return firestore
            .collection("categorias")
            .document(id)
            .set(dados)
            .addOnSuccessListener {
                activity.exibirMensagem("Categoria atualizada com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao atualizar categoria")
            }.isSuccessful
    }

    override suspend fun atualizarCategoriaUrl(
        activity: Activity, id: String, categoria: String, uri: String, url: String
    ): Boolean {
        val dados = mapOf(
            "imagem" to url,
            "nome" to categoria,
            "id" to id,
            "uri" to uri
        )
        return firestore
            .collection("categorias")
            .document(id)
            .set(dados)
            .addOnSuccessListener {
                activity.exibirMensagem("Categoria atualizada com sucesso")
            }.addOnFailureListener {
                activity.exibirMensagem("Erro ao atualizar categoria")
            }.isSuccessful
    }
}

