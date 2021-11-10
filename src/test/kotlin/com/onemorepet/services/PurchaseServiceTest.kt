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
    val petOffers = listOf(
        PetOffer("cat", mapOf(Pair("simple", 0.9)), 1, 1000, Location(1000.0, 1000.0), 5),
        PetOffer("dog", mapOf(Pair("not simple", 0.3)), 2, 5000, Location(0.0, 0.0), 1)
    )
    val users = listOf(
        User("kek", Location(0.0, 0.0), balance = 10000),
        User("kek", Location(0.0, 0.0), balance = 10)
    )

    @BeforeEach
    fun fill() {
        saleFeedService.addOffers(petOffers)
    }

    @AfterEach
    fun clear() {
        saleFeedService.clear()
    }

    @Test
    fun testEnoughMoney() {
        val user = users[0]
        val petOffer = petOffers[0]
        purchaseService.buy(user, petOffer)
        Assertions.assertEquals(
            9000,
            user.balance
        )
        Assertions.assertEquals(
            saleFeedService.petOffers,
            listOf(petOffers[1])
        )
    }

    @Test
    fun testNotEnoughMoney() {
        val user = users[1]
        val petOffer = petOffers[1]
        Assertions.assertThrows(
            IllegalStateException::class.java
        ) { purchaseService.buy(user, petOffer) }

        Assertions.assertEquals(
            10,
            user.balance
        )
        Assertions.assertEquals(
            saleFeedService.petOffers,
            petOffers
        )
    }
}