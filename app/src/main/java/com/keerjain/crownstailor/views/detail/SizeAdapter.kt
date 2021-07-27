package com.keerjain.crownstailor.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.detail.OrderDetail
import com.keerjain.crownstailor.databinding.OrderDetailListItemBinding
import com.keerjain.crownstailor.databinding.SizeLayoutBinding

class SizeAdapter : RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {
    private val listSize = ArrayList<OrderDetail>()

    fun setSizeList(list: List<OrderDetail>?) {
        if (list == null) return

        listSize.clear()
        listSize.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val sizeLayoutBinding =
            SizeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeViewHolder(sizeLayoutBinding)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = listSize[position]
        holder.bind(size)
    }

    override fun getItemCount(): Int = listSize.size

    inner class SizeViewHolder(private val binding: SizeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (size: OrderDetail) {
            binding.tvNeck.text = binding.tvNeck.context.resources.getString(
                R.string.length_format,
                String.format("%.0f", size.neckSize)
            )
            binding.tvWaist.text = binding.tvNeck.context.resources.getString(
                R.string.length_format,
                String.format("%.0f", size.waistSize)
            )
            binding.tvHeight.text = binding.tvNeck.context.resources.getString(
                R.string.length_format,
                String.format("%.0f", size.bodyHeight)
            )
            binding.tvChest.text = binding.tvNeck.context.resources.getString(
                R.string.length_format,
                String.format("%.0f", size.chestSize)
            )
            binding.tvWeight.text = binding.tvNeck.context.resources.getString(
                R.string.weight_format,
                String.format("%.0f", size.bodyWeight)
            )
            binding.tvArm.text = binding.tvNeck.context.resources.getString(
                R.string.length_format,
                String.format("%.0f", size.armSize)
            )
            binding.tvOfferInstruction.text = size.instructions
        }
    }
}