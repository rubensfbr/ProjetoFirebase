package com.example.projetofirebase.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofirebase.domain.repository.CadastroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CadastroViewModel @Inject constructor(private val repository: CadastroRepository) :
    ViewModel() {

    fun cadastrarUsuario(context: Context, email: String, senha: String) {
        viewModelScope.launch {
            repository.cadastrarUsuario(context, email, senha)
        }
    }
}