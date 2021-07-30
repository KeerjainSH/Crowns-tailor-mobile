package com.keerjain.crownstailor.utils

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object ShimmerDrawableInit {
    private val shimmer =
        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1500) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

    // This is the placeholder for the imageView
    private val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    fun getShimmerDrawable() = shimmerDrawable
}