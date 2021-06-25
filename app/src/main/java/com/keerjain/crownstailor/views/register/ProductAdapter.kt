package com.keerjain.crownstailor.views.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.databinding.ProductCheckboxListBinding
import com.keerjain.crownstailor.databinding.TransactionListItemBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val listProduct = ArrayList<Product>()

    inner class ProductViewHolder(private val binding: ProductCheckboxListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.itemCheckBox.text = product.productName
        }
    }

    fun setProductList(list: List<Product>?) {
        if (list == null) return

        this.listProduct.clear()
        this.listProduct.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productCheckboxListBinding =
            ProductCheckboxListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(productCheckboxListBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = listProduct[position]

        holder.bind(product)
    }

    override fun getItemCount(): Int = listProduct.size
}