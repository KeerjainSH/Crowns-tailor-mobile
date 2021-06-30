package com.keerjain.crownstailor.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.databinding.OrderDetailListItemBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listProduct = ArrayList<ProductListItem>()

    interface OnItemClickCallback {
        fun onItemClicked(data: ProductListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setProductList(list: List<ProductListItem>?) {
        if (list == null) return

        this.listProduct.clear()
        this.listProduct.addAll(list)
        notifyDataSetChanged()
    }

    inner class OrderDetailViewHolder(private val binding: OrderDetailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductListItem) {
            binding.tvProductName.text = product.productDetail.productName
            binding.imgProductList.loadPicture(product.productDetail.productPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val orderDetailListItemBinding =
            OrderDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailViewHolder(orderDetailListItemBinding)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val order = listProduct[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listProduct[holder.absoluteAdapterPosition])
        }

        holder.bind(order)
    }

    override fun getItemCount(): Int = listProduct.size
}