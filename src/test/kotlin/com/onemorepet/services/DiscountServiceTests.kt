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
    val catOffer = PetOffer("cat", mapOf("simple" to 0.9), 1, 1000, Location(1000.0, 1000.0), 5)
    val dogOffer = PetOffer("dog", mapOf("not simple" to 0.3), 2, 5000, Location(0.0, 0.0), 1)

    val userWithoutDiscount = User("Alex", Location(0.0, 0.0))
    val userWithAllDiscount = User("Andrew", Location(0.0, 0.0), mutableListOf(Discount(0.5, mutableListOf(null))))
    val userWithDogDiscount = User("Anna", Location(0.0, 0.0), mutableListOf(Discount(0.5, mutableListOf("dog"))))
    val userWithSeveralDiscount = User(
        "Sasha", Location(0.0, 0.0),
        mutableListOf(
            Discount(0.5, mutableListOf("dog")), Discount(0.5, mutableListOf("cat"))
        )
    )
    val userWithSmallerSeveralDiscount = User(
        "Evgraf", Location(0.0, 0.0),
        mutableListOf(
            Discount(0.5, mutableListOf("cat")), Discount(0.25, mutableListOf("cat"))
        )
    )
    val userWithMultipleDiscount = User(
        "Macbook",
        Location(0.0, 0.0),
        mutableListOf(Discount(0.5, mutableListOf("cat", "dog")))
    )

    @Test
    fun testWithoutDiscount() {
        assertEquals(catOffer.price, discountService.calcPrice(user = userWithoutDiscount, catOffer))
    }

    @Test
    fun testWithDiscount() {
        assertEquals(500, discountService.calcPrice(user = userWithAllDiscount, catOffer))
    }

    @Test
    fun testWithSpecificDiscount() {
        assertEquals(catOffer.price, discountService.calcPrice(user = userWithDogDiscount, catOffer))
    }

    @Test
    fun testSeveralDiscount() {
        assertEquals(500, discountService.calcPrice(user = userWithSeveralDiscount, catOffer))
        assertEquals(2500, discountService.calcPrice(user = userWithSeveralDiscount, dogOffer))
    }

    @Test
    fun testOptimalDiscount() {
        assertEquals(250, discountService.calcPrice(user = userWithSmallerSeveralDiscount, catOffer))
    }

    @Test
    fun testMultipleDiscount() {
        assertEquals(500, discountService.calcPrice(user = userWithMultipleDiscount, catOffer))
        assertEquals(2500, discountService.calcPrice(user = userWithMultipleDiscount, dogOffer))
    }

    @Test
    fun testGetAllWithDogDiscount() {
        val data = mutableListOf(userWithDogDiscount, userWithSeveralDiscount)
        assertEquals(
            data,
            discountService.getUserWithSpecifiedDiscount(data, "dog")
        )
    }
}
