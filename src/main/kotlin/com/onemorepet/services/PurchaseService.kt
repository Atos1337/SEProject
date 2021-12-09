package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    @Autowired private val saleFeedService: SaleFeedService,
    @Autowired private val discountService: DiscountService
) {
    fun buy(user: User, petOffer: PetOffer) {
        val price: Int = discountService.calcPrice(user, petOffer)
        if (price <= user.balance) {
            saleFeedService.removeOffer(petOffer)
            user.balance -= price
        } else {
            throw IllegalStateException("Not enough money")
        }
    }
}
