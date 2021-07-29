package com.keerjain.crownstailor.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.detail.DesignDetail
import com.keerjain.crownstailor.databinding.DesignListItemBinding

class DesignAdapter : RecyclerView.Adapter<DesignAdapter.DesignViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    val listDesign = ArrayList<DesignDetail>()

    interface OnItemClickCallback {
        fun onItemClicked(data: DesignDetail)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class DesignViewHolder(private val binding: DesignListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(design: DesignDetail, position: Int) {
            binding.tvDesignNumber.text = binding.tvDesignNumber.context.resources.getString(R.string.design_number, position.toString())
            binding.tvDesignInstruction.text = design.deskripsi
        }
    }

    fun setDesignList(list: List<DesignDetail>?) {
        if (list == null) return

        listDesign.clear()
        listDesign.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignViewHolder {
        val designBinding =
            DesignListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DesignViewHolder(designBinding)
    }

    override fun onBindViewHolder(holder: DesignViewHolder, position: Int) {
        val design = listDesign[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listDesign[holder.absoluteAdapterPosition])
        }

        holder.bind(design, position)
    }

    override fun getItemCount(): Int = listDesign.size
}