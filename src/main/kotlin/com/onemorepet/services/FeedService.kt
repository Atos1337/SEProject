package com.onemorepet.services

import com.onemorepet.models.PetOffer
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

    fun filterByParameters(kind: String? = null, ageRange: IntRange? = null, priceRange: IntRange? = null): List<PetOffer> {
        return _petOffers.filter { offer ->
            (kind == null || kind == offer.kind) and (ageRange == null || offer.age in ageRange)
        }
    }
}