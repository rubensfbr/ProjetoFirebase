package com.example.projetofirebase.domain.repository

import android.content.Context

interface CadastroRepository {

    suspend fun cadastrarUsuario(context: Context, email: String, senha: String)

}