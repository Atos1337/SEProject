package com.onemorepet.services

import com.onemorepet.models.Location
import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PurchaseServiceTest(
    @Autowired val saleFeedService: SaleFeedService,
    @Autowired val purchaseService: PurchaseService
) {
    val catOffer = PetOffer("cat", mapOf("simple" to 0.9), 1, 1000, Location(1000.0, 1000.0), 5)
    val dogOffer = PetOffer("dog", mapOf("not simple" to 0.3), 2, 5000, Location(0.0, 0.0), 1)

    val enoughMoneyUser = User("Vova", Location(0.0, 0.0), balance = 10000)
    val notEnoughMoneyUser = User("Sasha", Location(0.0, 0.0), balance = 10)

    @BeforeEach
    fun fill() {
        saleFeedService.addOffers(listOf(catOffer, dogOffer))
    }

    @AfterEach
    fun clear() {
        saleFeedService.clear()
    }

    @Test
    fun testEnoughMoney() {
        val user = enoughMoneyUser
        val petOffer = catOffer
        purchaseService.buy(user, petOffer)
        Assertions.assertEquals(
            9000,
            user.balance
        )
        Assertions.assertEquals(
            saleFeedService.petOffers,
            listOf(dogOffer)
        )
    }

    @Test
    fun testNotEnoughMoney() {
        val user = notEnoughMoneyUser
        val petOffer = dogOffer
        Assertions.assertThrows(
            IllegalStateException::class.java
        ) { purchaseService.buy(user, petOffer) }

        Assertions.assertEquals(
            10,
            user.balance
        )
        Assertions.assertEquals(
            saleFeedService.petOffers,
            listOf(catOffer, dogOffer)
        )
    }
}
