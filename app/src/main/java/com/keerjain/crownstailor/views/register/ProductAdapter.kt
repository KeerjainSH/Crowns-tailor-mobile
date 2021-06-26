package com.keerjain.crownstailor.views.register

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.data.entities.product.Product
import com.keerjain.crownstailor.databinding.ProductCheckboxListBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listProduct = ArrayList<Product>()
    private val listProductChecked = ArrayList<Product>()

    interface OnItemClickCallback {
        fun onItemClicked(view: View, pos: Int)
    }

    inner class ProductViewHolder(binding: ProductCheckboxListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val checkBox = binding.itemCheckBox

        init {
            checkBox.setOnClickListener(this)
        }

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this@ProductAdapter.onItemClickCallback = onItemClickCallback
        }

        fun bind(product: Product) {
            checkBox.text = product.productName
        }

        override fun onClick(v: View) {
            this@ProductAdapter.onItemClickCallback.onItemClicked(v, layoutPosition)
        }
    }

    fun setProductList(list: List<Product>?) {
        if (list == null) return

        listProduct.clear()
        listProduct.addAll(list)
        notifyDataSetChanged()
    }

    fun addToCheckedList(product: Product) {
        listProductChecked.add(product)
    }

    fun removeFromCheckedList(product: Product) {
        listProductChecked.remove(product)
    }

    fun getCheckedProductList() : List<Product> = listProductChecked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productCheckboxListBinding =
            ProductCheckboxListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(productCheckboxListBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = listProduct[position]

        holder.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(view: View, pos: Int) {
                val checkBox = view as CheckBox
                val data = listProduct[pos]

                if (checkBox.isChecked) {
                    addToCheckedList(data)
                    Log.d("Checked", "Checked ${data.productName}")
                } else if (!checkBox.isChecked) {
                    removeFromCheckedList(data)
                    Log.d("Unchecked", "Unchecked ${data.productName}")
                }
            }
        })

        holder.bind(product)
    }

    override fun getItemCount(): Int = listProduct.size
}