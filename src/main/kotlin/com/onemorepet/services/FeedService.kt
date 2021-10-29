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

    fun filterByKind(kind: String) {
        throw NotImplementedError()
    }
}