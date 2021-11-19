package com.edgarmarcopolo.neptune.products.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edgarmarcopolo.neptune.databinding.ProductItemBinding
import com.edgarmarcopolo.neptune.products.ui.models.ProductUI
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MallsAdapter(private val list : List<ProductUI>, val viewModel : ProductsViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MallListViewHolder(ProductItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mallHolder = holder as MallListViewHolder
        mallHolder.bind(list[position], viewModel)
    }

    override fun getItemCount(): Int = list.size
}

@ExperimentalCoroutinesApi
class MallListViewHolder(var binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUI, viewModel: ProductsViewModel?) {
        binding.viewModel = viewModel
        binding.item = product
        binding.executePendingBindings()
    }
}