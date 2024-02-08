package com.example.projetofirebase.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofirebase.data.model.Categoria
import com.example.projetofirebase.domain.repository.MainFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: MainFragmentRepository) :
    ViewModel() {

    private val categoria = MutableLiveData<List<Categoria>>()
    val _categoria: LiveData<List<Categoria>> = categoria

    fun recuperarDados() {
        viewModelScope.launch {
            categoria.postValue(repository.recuperarDados())
        }
    }

    fun deletarCategoria(activity: Activity, id: String) {
        viewModelScope.launch {
            repository.deletarCategoria(activity, id)
        }
    }
}