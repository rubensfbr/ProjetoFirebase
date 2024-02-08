package com.example.projetofirebase.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.projetofirebase.data.model.Categoria
import com.example.projetofirebase.databinding.ItemRecyclerMainBinding
import com.squareup.picasso.Picasso

class CategoriaAdapter(
    private val onDelete: (Categoria) -> Unit,
    private val onClick: (Categoria) -> Unit
) : ListAdapter<Categoria, CategoriaViewHolder>(diffCallback) {

    fun setList(list: List<Categoria>) {
        this.submitList(ArrayList(list))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        return CategoriaViewHolder(
            ItemRecyclerMainBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(getItem(position), onDelete, onClick)
    }

    companion object {
        val diffCallback = object : ItemCallback<Categoria>() {
            override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CategoriaViewHolder(private val binding: ItemRecyclerMainBinding) : ViewHolder(binding.root) {

    fun bind(item: Categoria, onDelete: (Categoria) -> Unit, onClick: (Categoria) -> Unit) {

        binding.textCategoria.text = item.nome
        Picasso.get()
            .load(item.imagem)
            .into(binding.imagemCategoria)

        binding.clItemMain.setOnLongClickListener {
            onDelete(item)
            true
        }

        binding.clItemMain.setOnClickListener {
            onClick(item)
        }
    }

}