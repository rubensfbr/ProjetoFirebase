package com.example.projetofirebase.domain.repository

import android.app.Activity

interface CategoriaRepository {

    suspend fun salvarCategoria(activity: Activity, categoria: String, uri: String): Boolean

    suspend fun atualizarCategoriaUri(
        activity: Activity, id: String, categoria: String, uri: String
    ): Boolean


    suspend fun atualizarCategoriaUrl(
        activity: Activity, id: String, categoria: String, uri: String, url: String
    ): Boolean

}


