package com.example.projetofirebase.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofirebase.data.model.Produtos
import com.example.projetofirebase.domain.repository.ProdutosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutosViewModel @Inject constructor(private val repository: ProdutosRepository) :
    ViewModel() {

    private val produtos = MutableLiveData<List<Produtos>>()
    val _produtos: LiveData<List<Produtos>> = produtos

    fun recuperarListaProdutos(activity: Activity, categoria: String) {
        viewModelScope.launch {
            produtos.postValue(repository.recuperarListaProdutos(activity, categoria))
        }
    }

    fun deletarProdutos(activity: Activity, idCategoria: String, idProduto: String) {
        viewModelScope.launch {
            repository.deletarProduto(activity, idCategoria, idProduto)
        }
    }
}