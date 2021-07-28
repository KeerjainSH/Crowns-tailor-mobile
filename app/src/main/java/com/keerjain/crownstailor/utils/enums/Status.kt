package com.keerjain.crownstailor.utils.enums

import com.keerjain.crownstailor.R

enum class Status {
    PAID_ORDER {
        override fun getStringResources() = R.string.pesanan_dibayar
    },
    FINISHED {
        override fun getStringResources() = R.string.selesai
    };

    abstract fun getStringResources(): Int
}