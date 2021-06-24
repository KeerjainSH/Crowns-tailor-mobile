package com.keerjain.crownstailor.utils.enums

import com.keerjain.crownstailor.R

enum class Status {
    NEW_ORDER {
        override fun getStringResources() = R.string.pesanan_baru
    },
    ON_PROGRESS {
        override fun getStringResources() = R.string.dikerjakan
    },
    ON_DELIVERY {
        override fun getStringResources() = R.string.dikirim
    },
    FINISHED {
        override fun getStringResources() = R.string.selesai
    };

    abstract fun getStringResources(): Int
}