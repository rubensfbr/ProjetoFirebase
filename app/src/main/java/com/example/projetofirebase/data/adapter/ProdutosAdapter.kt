package com.example.projetofirebase.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofirebase.data.model.Produtos
import com.example.projetofirebase.databinding.ItemRecyclerProdutoBinding
import com.squareup.picasso.Picasso

class ProdutosAdapter(private val onDelete: (Produtos) -> Unit) :
    ListAdapter<Produtos, ProdutosViewHolder>(diffCallback) {

    fun setList(list: List<Produtos>) {
        this.submitList(ArrayList(list))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {
        return ProdutosViewHolder(
            ItemRecyclerProdutoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        holder.bind(getItem(position), onDelete)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Produtos>() {
            override fun areItemsTheSame(oldItem: Produtos, newItem: Produtos): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Produtos, newItem: Produtos): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ProdutosViewHolder(private val binding: ItemRecyclerProdutoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Produtos, onDelete: (Produtos) -> Unit) {

        binding.textNomeProduto.text = item.nome
        binding.textValor.text = "R$ ${item.preco},00"
        Picasso.get()
            .load(item.imagem)
            .into(binding.imageProduto)

        binding.cvProduto.setOnLongClickListener {
            onDelete(item)
            true
        }
    }
}