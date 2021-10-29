package com.onemorepet.models

data class PetOffer(val kind: String, val species: List<Pair<String, Float>>, val age: UInt, val price: UInt)
