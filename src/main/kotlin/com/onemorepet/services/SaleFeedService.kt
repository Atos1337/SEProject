package com.onemorepet.services

import com.onemorepet.models.PetOffer
import com.onemorepet.models.User
import org.springframework.stereotype.Service

@Service
class SaleFeedService {
    private var _petOffers: MutableList<PetOffer> = mutableListOf()
    val petOffers: List<PetOffer> get() = _petOffers

    fun addOffers(petOffers: List<PetOffer>) {
        _petOffers.addAll(petOffers)
    }

    fun clear() {
        _petOffers.clear()
    }

    fun filterByParameters(
        user: User? = null,
        kind: String? = null,
        ageRange: IntRange? = null,
        priceRange: IntRange? = null,
        species: Map<String, Double?>? = null,
        radius: Double? = null,
        count: Int? = null,
    ): List<PetOffer> {
        return _petOffers.filter { offer ->
            (kind == null || kind == offer.kind) &&
                (ageRange == null || offer.age in ageRange) &&
                (priceRange == null || offer.price in priceRange) &&
                (
                    species == null || species.all {
                        it.key in offer.species &&
                            (
                                it.value?.let { it_proportion ->
                                    offer.species[it.key]?.let { offer_proportion ->
                                        it_proportion <= offer_proportion
                                    } ?: false
                                } ?: true
                                )
                    }
                    ) &&
                (
                    radius == null || user?.let {
                        user.location.distance(offer.location) <= radius
                    } ?: false
                    ) && (count == null || count <= offer.count)
        }
    }
}