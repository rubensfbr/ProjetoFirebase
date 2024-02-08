package com.example.projetofirebase.domain.repository

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginRepository {

    suspend fun verificarUsuarioLogado(): Boolean

    suspend fun logarUsuario(context: Context, email: String, senha: String): Task<AuthResult>

}