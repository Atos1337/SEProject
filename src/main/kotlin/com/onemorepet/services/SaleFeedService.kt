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
            (kind?.let { it == offer.kind } ?: true) &&
                (ageRange?.let { offer.age in it } ?: true) &&
                (priceRange?.let { offer.price in it } ?: true) &&
                (
                    species?.all { entry ->
                        entry.key in offer.species &&
                            (
                                entry.value?.let { it_proportion ->
                                    offer.species[entry.key]?.let { offer_proportion ->
                                        it_proportion <= offer_proportion
                                    } ?: false
                                } ?: true
                                )
                    } ?: true
                    ) &&
                (
                    radius == null || user?.let {
                        user.location.distance(offer.location) <= radius
                    } ?: false
                    ) &&
                (count?.let { it <= offer.count } ?: true)
        }
    }

    fun removeOffer(petOffer: PetOffer) {
        _petOffers.removeIf { it == petOffer }
    }
}
