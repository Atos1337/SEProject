package com.onemorepet.services

import com.onemorepet.models.Discount
import com.onemorepet.models.Location
import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DiscountServiceTests(@Autowired val discountService: DiscountService) {
    val petOffers = listOf(
        PetOffer("cat", mapOf(Pair("simple", 0.9)), 1, 1000, Location(1000.0, 1000.0), 5),
        PetOffer("dog", mapOf(Pair("not simple", 0.3)), 2, 5000, Location(0.0, 0.0), 1)
    )
    val users = listOf(
        User("kek", Location(0.0, 0.0)),
        User("lol", Location(0.0, 0.0), mutableListOf(Discount(0.5, null))),
        User("zhopa", Location(0.0, 0.0), mutableListOf(Discount(0.5, "not simple")))
    )

    @Test
    fun testWithoutDiscount() {
        assertEquals(petOffers[0].price, discountService.calcPrice(user = users[0], petOffers[0]))
    }

    @Test
    fun testWithDiscount() {
        assertEquals(500, discountService.calcPrice(user = users[1], petOffers[0]))
    }

    @Test
    fun testWithSpecificDiscount() {
        assertEquals(petOffers[0].price, discountService.calcPrice(user = users[2], petOffers[0]))
    }
}