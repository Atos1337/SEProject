package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun calcPrice(user: User, offer: PetOffer): Int {
        if (user.discounts == null) {
            return offer.price
        }
        for (discount in user.discounts) {
            if (discount.kind == null || discount.kind == offer.kind) {
                return (offer.price * discount.value).toInt()
            }
        }
        return offer.price
    }
}