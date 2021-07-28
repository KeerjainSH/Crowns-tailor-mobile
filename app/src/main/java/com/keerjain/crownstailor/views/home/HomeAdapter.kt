package com.keerjain.crownstailor.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.TransactionListItemBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture
import com.keerjain.crownstailor.utils.enums.Status

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listOrder = ArrayList<TransactionListItem>()

    interface OnItemClickCallback {
        fun onItemClicked(data: TransactionListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOrdersList(list: List<TransactionListItem>?) {
        if (list == null) return

        this.listOrder.clear()
        this.listOrder.addAll(list.take(3))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val orderListItemBinding =
            TransactionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(orderListItemBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val order = listOrder[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listOrder[holder.absoluteAdapterPosition])
        }

        holder.bind(order)
    }

    override fun getItemCount(): Int = listOrder.size

    inner class HomeViewHolder(private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: TransactionListItem) {
            binding.tvOrderId.text = binding.tvOrderId.context.resources.getString(R.string.invoice_number, order.trxId.toString())
            binding.tvProductName.text = order.productName

            val status = order.transactionStatus
            binding.tvStatus.text = binding.tvStatus.context.resources.getString(status.getStringResources())

            when(status) {
                Status.PAID_ORDER -> {
                    binding.tvStatus.setBackgroundResource(
                        R.drawable.status_baru
                    )
                }

                Status.FINISHED -> {
                    binding.tvStatus.setBackgroundResource(
                        R.drawable.status_selesai
                    )
                }
            }

            binding.productPhoto.loadPicture(order.productPhoto)
        }
    }
}