package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun calcPrice(user: User, offer: PetOffer): Int {
        if (user.discounts == null || (!user.discounts.kind.equals(offer.kind) && user.discounts.kind != null)) {
            return offer.price
        }
        return (offer.price * user.discounts.value).toInt()
    }
}