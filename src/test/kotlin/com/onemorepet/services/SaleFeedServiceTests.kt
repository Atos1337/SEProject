package com.onemorepet.services

import com.onemorepet.models.Location
import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SaleFeedServiceTests(@Autowired val feedService: SaleFeedService) {

    val catOffer = PetOffer("cat", mapOf("simple" to 0.9), 1, 1000, Location(1000.0, 1000.0), 5)
    val dogOffer = PetOffer("dog", mapOf("not simple" to 0.3), 2, 5000, Location(0.0, 0.0), 1)

    val user = User("Vova", Location(0.0, 0.0))

    @BeforeEach
    fun fill() {
        feedService.addOffers(listOf(catOffer, dogOffer))
    }

    @AfterEach
    fun clear() {
        feedService.clear()
    }

    @Test
    fun testShowAll() {
        assertEquals(feedService.petOffers, listOf(catOffer, dogOffer))
    }

    @Test
    fun filterByKind() {
        assertEquals(feedService.filterByParameters(kind = "dog"), listOf(dogOffer))
    }

    @Test
    fun filterByAge() {
        assertEquals(feedService.filterByParameters(ageRange = 2..2), listOf(dogOffer))
    }

    @Test
    fun filterByPrice() {
        assertEquals(feedService.filterByParameters(priceRange = 1500..10000), listOf(dogOffer))
    }

    @Test
    fun filterBySpecies() {
        assertEquals(feedService.filterByParameters(species = mapOf("not simple" to null)), listOf(dogOffer))
    }

    @Test
    fun filterBySpeciesProportion() {
        assertEquals(feedService.filterByParameters(species = mapOf("simple" to 0.5)), listOf(catOffer))
        assertEquals(feedService.filterByParameters(species = mapOf("not simple" to 0.5)), emptyList<PetOffer>())
    }

    @Test
    fun filterByRadius() {
        assertEquals(
            feedService.filterByParameters(user = user, radius = 1000.0),
            listOf(dogOffer)
        )
    }

    @Test
    fun filterByOfferSize() {
        assertEquals(
            feedService.filterByParameters(count = 5),
            listOf(catOffer)
        )
    }

    @Test
    fun testRemoveOfferThatExists() {
        feedService.removeOffer(catOffer)
        assertEquals(
            feedService.petOffers,
            listOf(dogOffer)
        )
    }

    @Test
    fun testRemoveOfferThatNotExists() {
        feedService.removeOffer(PetOffer("cat", mapOf("simple" to 1.0), 1, 1000, Location(1000.0, 1000.0), 5))
        assertEquals(
            feedService.petOffers,
            listOf(catOffer, dogOffer)
        )
    }
}
