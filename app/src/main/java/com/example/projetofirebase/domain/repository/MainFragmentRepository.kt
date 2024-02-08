package com.example.projetofirebase.domain.repository

import android.app.Activity
import com.example.projetofirebase.data.model.Categoria

interface MainFragmentRepository {

    suspend fun recuperarDados(): List<Categoria>

    suspend fun deletarCategoria(activity: Activity, id: String)

}