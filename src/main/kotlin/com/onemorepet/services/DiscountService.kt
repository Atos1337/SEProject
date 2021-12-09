package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.stereotype.Service
import java.lang.Integer.min

@Service
class DiscountService {
    fun calcPrice(user: User, offer: PetOffer): Int {
        if (user.discounts == null) {
            return offer.price
        }
        var price = offer.price
        for (discount in user.discounts) {
            for (kind in discount.kinds) {
                if (kind == null || kind == offer.kind) {
                    price = min(price, (offer.price * discount.value).toInt())
                }
            }
        }
        return price
    }

    fun getUserWithSpecifiedDiscount(users: List<User>, kind: String): List<User> {
        val specifiedUsers = mutableListOf<User>()
        for (user in users) {
            if (user.discounts == null) {
                continue
            }
            for (discount in user.discounts) {
                for (cur_kind in discount.kinds) {
                    if (cur_kind.equals(kind)) {
                        specifiedUsers.add(user)
                    }
                }
            }
        }
        return specifiedUsers
    }
}
