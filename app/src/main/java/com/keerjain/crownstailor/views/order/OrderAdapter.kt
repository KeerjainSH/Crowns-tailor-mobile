package com.keerjain.crownstailor.views.order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.transaction.TransactionListItem
import com.keerjain.crownstailor.databinding.TransactionListItemBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.loadPicture
import com.keerjain.crownstailor.utils.enums.Status

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(), Filterable {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listOrder = ArrayList<TransactionListItem>()
    private var listOrderFiltered = ArrayList<TransactionListItem>()
    private var listOrderStatusFiltered = ArrayList<TransactionListItem>()
    private val fullList = ArrayList<TransactionListItem>()

    interface OnItemClickCallback {
        fun onItemClicked(data: TransactionListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOrdersList(list: List<TransactionListItem>?) {
        if (list == null) return

        listOrder.clear()
        listOrder.addAll(list)
        setFullList(list)
        listOrderFiltered = listOrder
        listOrderStatusFiltered = listOrderFiltered

        notifyDataSetChanged()
    }

    private fun setFullList(list: List<TransactionListItem>) {
        fullList.clear()
        fullList.addAll(list)
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
        val order = listOrderFiltered[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listOrderFiltered[holder.absoluteAdapterPosition])
        }

        holder.bind(order)
    }

    override fun getItemCount(): Int = listOrderFiltered.size

    inner class OrderViewHolder(private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: TransactionListItem) {
            binding.tvOrderId.text = binding.tvOrderId.context.resources.getString(
                R.string.invoice_number,
                order.trxId.toString()
            )
            binding.tvProductName.text = order.productName

            val status = order.transactionStatus
            binding.tvStatus.text =
                binding.tvStatus.context.resources.getString(status.getStringResources())

            when (status) {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                listOrderFiltered = if (charSearch.isEmpty()) {
                    listOrderStatusFiltered
                } else {
                    val resultList = ArrayList<TransactionListItem>()
                    for (data in listOrderStatusFiltered) {
                        if (data.trxId.toString().lowercase()
                                .contains(charSearch.lowercase()) or data.productName.lowercase()
                                .contains(charSearch.lowercase())
                        ) {
                            resultList.add(data)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = listOrderFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listOrderFiltered = results?.values as ArrayList<TransactionListItem>
                notifyDataSetChanged()
            }
        }
    }

    fun filterWithStatus(status: Status?) {
        if (status != null) {
            val list = fullList
            val filtered = list.filter {
                it.transactionStatus == status
            }
            listOrderStatusFiltered.clear()
            listOrderStatusFiltered.addAll(filtered)
            notifyDataSetChanged()
        } else {
            listOrderStatusFiltered.clear()
            listOrderStatusFiltered.addAll(fullList)
            notifyDataSetChanged()
        }
    }
}