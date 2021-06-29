package com.keerjain.crownstailor.utils.enums

import com.keerjain.crownstailor.R

enum class OfferStatus {
    NEW_OFFER {
        override fun getStringResources() = R.string.new_offer
    },
    PRICE_SENT {
        override fun getStringResources() = R.string.price_sent
    },
    PRICE_DECLINED {
        override fun getStringResources() = R.string.price_declined
    },
    PRICE_ACCEPTED {
        override fun getStringResources() = R.string.price_accepted
    },
    NEW_PRICE {
        override fun getStringResources() = R.string.new_price
    };

    abstract fun getStringResources(): Int
}