package com.onemorepet.services

import com.onemorepet.models.PetOffer
import org.junit.jupiter.api.AfterEach

import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
internal class SaleFeedServiceTests(@Autowired val feedService: SaleFeedService) {

    val petOffers = listOf(
        PetOffer("cat", listOf(Pair("simple", 1.0)), 1, 1000),
        PetOffer("dog", listOf(Pair("simple", 1.0)), 1, 5000)
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
        assertEquals(feedService.filterByKind("dog"), listOf(petOffers[1]))
    }

}