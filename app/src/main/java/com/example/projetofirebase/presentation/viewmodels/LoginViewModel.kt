package com.example.projetofirebase.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofirebase.domain.repository.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private var user = false
    private lateinit var authResult: Task<AuthResult>

    fun verificarUsuarioLogado(): Boolean {
        viewModelScope.launch {
            user = repository.verificarUsuarioLogado()
        }
        return user
    }

    fun logarUsuario(context: Context, email: String, senha: String): Task<AuthResult> {
        viewModelScope.launch {
            authResult = repository.logarUsuario(context, email, senha)
        }
        return authResult
    }
}