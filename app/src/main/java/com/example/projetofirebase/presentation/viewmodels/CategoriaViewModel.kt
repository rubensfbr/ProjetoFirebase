package com.example.projetofirebase.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.projetofirebase.domain.repository.CategoriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(private val repository: CategoriaRepository) :
    ViewModel() {

    suspend fun salvarCategoria(activity: Activity, categoria: String, uri: String): Boolean {
        return repository.salvarCategoria(activity, categoria, uri)
    }

    suspend fun atualizarCategoriaUri(
        activity: Activity, id: String, categoria: String, uri: String
    ): Boolean {
        return repository.atualizarCategoriaUri(activity, id, categoria, uri)
    }

    suspend fun atualizarCategoriaUrl(
        activity: Activity, id: String, categoria: String, uri: String, url: String
    ): Boolean {
        return repository.atualizarCategoriaUrl(activity, id, categoria, uri, url)
    }
}