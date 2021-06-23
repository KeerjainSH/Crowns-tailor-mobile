package com.keerjain.crownstailor.views.offer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.offer.OfferListItem
import com.keerjain.crownstailor.databinding.OfferListItemBinding
import com.keerjain.crownstailor.utils.ExtensionFunctions.setProfilePicture

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listOffer = ArrayList<OfferListItem>()

    interface OnItemClickCallback {
        fun onItemClicked(data: OfferListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOffers(list: List<OfferListItem>?) {
        if (list == null) return

        this.listOffer.clear()
        this.listOffer.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val offerListItemBinding =
            OfferListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(offerListItemBinding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = listOffer[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listOffer[holder.absoluteAdapterPosition])
        }

        holder.bind(offer)
    }

    override fun getItemCount() = listOffer.size

    inner class OfferViewHolder(private val binding: OfferListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: OfferListItem) {
            binding.tvUserName.text = offer.customerDetail.userFullName
            binding.tvOfferDate.text = offer.offerDate
            binding.tvOfferType.text = binding.tvOfferType.context.resources.getString(
                R.string.offer_type_list,
                offer.productName
            )
            binding.userProfilePhoto.setProfilePicture(offer.customerDetail.photoProfile)
        }
    }
}