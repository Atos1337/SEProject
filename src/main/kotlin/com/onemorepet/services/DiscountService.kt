package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun calcPrice(user: User, offer: PetOffer): Int {
        return offer.price
    }
}