package com.keerjain.crownstailor.utils.enums

import com.keerjain.crownstailor.R

enum class OfferStatus {
    OFFER_NEW {
        override fun getStringResources() = R.string.new_offer
    },
    OFFER_RESPONSE_SENT {
        override fun getStringResources() = R.string.response_sent
    },
    OFFER_NEW_PRICE {
        override fun getStringResources() = R.string.new_price
    },
    OFFER_ACCEPTED {
        override fun getStringResources() = R.string.price_accepted
    };

    abstract fun getStringResources(): Int
}