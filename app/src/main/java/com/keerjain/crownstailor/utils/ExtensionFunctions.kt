package com.keerjain.crownstailor.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun TextView.showLoading(state: Boolean) {
        if (state) {
            this.background = ShimmerDrawableInit.getShimmerDrawable()
            this.setTextColor(
                ContextCompat.getColor(
                    this.context,
                    android.R.color.transparent
                )
            )
        } else {
            this.background = null
            this.setTextColor(
                ContextCompat.getColor(
                    this.context,
                    R.color.black
                )
            )
        }
    }
}