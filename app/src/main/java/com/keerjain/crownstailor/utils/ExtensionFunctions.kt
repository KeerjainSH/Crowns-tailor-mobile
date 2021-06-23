package com.keerjain.crownstailor.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.keerjain.crownstailor.R
import de.hdodenhof.circleimageview.CircleImageView

object ExtensionFunctions {
    fun CircleImageView.setProfilePicture(url: String) {
        Glide.with(this.context)
            .load(url)
            .apply {
                RequestOptions().override(400, 400)
                    .placeholder(ShimmerDrawableInit.shimmerDrawable)
                    .error(R.drawable.ic_profile_picture)
            }
            .into(this)
    }

    fun ImageView.loadPicture(url: String) {
        Glide.with(this.context)
            .load(url)
            .apply {
                RequestOptions()
                    .placeholder(ShimmerDrawableInit.shimmerDrawable)
                    .error(R.drawable.ic_broken_image)
            }
            .into(this)
    }
}