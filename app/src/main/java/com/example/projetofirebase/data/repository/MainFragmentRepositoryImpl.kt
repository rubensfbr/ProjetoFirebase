package com.example.projetofirebase.data.repository

import android.app.Activity
import com.example.projetofirebase.data.model.Categoria
import com.example.projetofirebase.domain.repository.MainFragmentRepository
import com.example.projetofirebase.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainFragmentRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : MainFragmentRepository {

    override suspend fun recuperarDados(): List<Categoria> {
        val listaCategorias = mutableListOf<Categoria>()
        listaCategorias.clear()
        firestore
            .collection("categorias")
            .get()
            .addOnFailureListener {}
            .await().toObjects(Categoria::class.java).run {
                listaCategorias.addAll(this)
            }
        return listaCategorias
    }

    override suspend fun deletarCategoria(activity: Activity, id: String) {
        firestore
            .collection("categorias")
            .document(id)
            .delete()
            .addOnSuccessListener {
                activity.exibirMensagem("Categoria deletada com sucesso")
            }
            .addOnFailureListener {
                activity.exibirMensagem("Erro ao deletar categoria")
            }
        storage
            .getReference("fotos")
            .child("categorias")
            .child(id)
            .delete()
            .addOnFailureListener {
                activity.exibirMensagem(it.message!!)
            }
    }
}
