package com.keerjain.crownstailor.views.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.TransactionListItemBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture
import com.keerjain.crownstailor.utils.enums.Status
import com.keerjain.crownstailor.views.home.HomeAdapter

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
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
        this.listOrder.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        val orderListItemBinding =
            TransactionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(orderListItemBinding)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val order = listOrder[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listOrder[holder.absoluteAdapterPosition])
        }

        holder.bind(order)
    }

    override fun getItemCount(): Int = listOrder.size

    inner class OrderViewHolder (private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: TransactionListItem) {
            binding.tvOrderId.text = binding.tvOrderId.context.resources.getString(R.string.invoice_number, order.trxId.toString())
            binding.tvProductName.text = order.productName

            val status = order.transactionStatus
            binding.tvStatus.text = binding.tvStatus.context.resources.getString(status.getStringResources())

            when(status) {
                Status.NEW_ORDER -> {
                    binding.tvStatus.setBackgroundResource(
                        R.drawable.status_baru
                    )
                }

                Status.ON_PROGRESS -> {
                    binding.tvStatus.setBackgroundResource(
                        R.drawable.status_dikerjakan
                    )
                }

                Status.ON_DELIVERY -> {
                    binding.tvStatus.setBackgroundResource(
                        R.drawable.status_dikirim
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