package com.onemorepet.services

import com.onemorepet.models.PetOffer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SaleFeedServiceTests(@Autowired val feedService: SaleFeedService) {

    val petOffers = listOf(
        PetOffer("cat", mapOf(Pair("simple", 0.9)), 1, 1000),
        PetOffer("dog", mapOf(Pair("not simple", 0.3)), 2, 5000)
    )

    @BeforeEach
    fun fill() {
        feedService.addOffers(petOffers)
    }

    @AfterEach
    fun clear() {
        feedService.clear()
    }

    @Test
    fun testShowAll() {
        assertEquals(feedService.petOffers, petOffers)
    }

    @Test
    fun filterByKind() {
        assertEquals(feedService.filterByParameters(kind = "dog"), listOf(petOffers[1]))
    }

    @Test
    fun filterByAge() {
        assertEquals(feedService.filterByParameters(ageRange = 2..2), listOf(petOffers[1]))
    }

    @Test
    fun filterByPrice() {
        assertEquals(feedService.filterByParameters(priceRange = 1500..10000), listOf(petOffers[1]))
    }

    @Test
    fun filterBySpecies() {
        assertEquals(feedService.filterByParameters(species = mapOf(Pair("not simple", null))), listOf(petOffers[1]))
    }

    @Test
    fun filterBySpeciesProportion() {
        assertEquals(feedService.filterByParameters(species = mapOf(Pair("simple", 0.5))), listOf(petOffers[1]))
        assertEquals(feedService.filterByParameters(species = mapOf(Pair("not simple", 0.5))), emptyList<PetOffer>())
    }
}