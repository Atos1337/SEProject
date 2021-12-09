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
        User("Alex", Location(0.0, 0.0)),
        User("Andrew", Location(0.0, 0.0), mutableListOf(Discount(0.5, mutableListOf(null)))),
        User("Anna", Location(0.0, 0.0), mutableListOf(Discount(0.5, mutableListOf("dog")))),
        User(
            "Sasha", Location(0.0, 0.0),
            mutableListOf(
                Discount(0.5, mutableListOf("dog")), Discount(0.5, mutableListOf("cat"))
            )
        ),
        User(
            "Evgraf", Location(0.0, 0.0),
            mutableListOf(
                Discount(0.5, mutableListOf("cat")), Discount(0.25, mutableListOf("cat"))
            )
        ),
        User("Macbook", Location(0.0, 0.0), mutableListOf(Discount(0.5, mutableListOf("cat", "dog"))))
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

    @Test
    fun testSeveralDiscount() {
        assertEquals(500, discountService.calcPrice(user = users[3], petOffers[0]))
        assertEquals(2500, discountService.calcPrice(user = users[3], petOffers[1]))
    }

    @Test
    fun testOptimalDiscount() {
        assertEquals(250, discountService.calcPrice(user = users[4], petOffers[0]))
    }

    @Test
    fun testMultipleDiscount() {
        assertEquals(500, discountService.calcPrice(user = users[5], petOffers[0]))
        assertEquals(2500, discountService.calcPrice(user = users[5], petOffers[1]))
    }

    @Test
    fun testGetAllWithDogDiscount() {
        val data = mutableListOf(users[2], users[3])
        assertEquals(
            data,
            discountService.getUserWithSpecifiedDiscount(data, "dog")
        )
    }
}
