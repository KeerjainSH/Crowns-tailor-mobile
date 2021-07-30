package com.keerjain.crownstailor.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.keerjain.crownstailor.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat
import java.util.*

object ExtensionFunctions {
    fun CircleImageView.setProfilePicture(url: String) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions()
                    .override(400, 400)
                    .placeholder(ShimmerDrawableInit.getShimmerDrawable())
                    .error(R.drawable.ic_profile_picture)
                    .fallback(R.drawable.ic_broken_image)
            )
            .into(this)
    }

    fun ImageView.loadPicture(url: String) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(ShimmerDrawableInit.getShimmerDrawable())
                    .error(R.drawable.ic_broken_image)
                    .fallback(R.drawable.ic_broken_image)
            )
            .into(this)
    }

    fun Float.formatToCurrency(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        currencyFormatter.maximumFractionDigits = 2
        currencyFormatter.currency = Currency.getInstance("IDR")

        return currencyFormatter.format(this)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}