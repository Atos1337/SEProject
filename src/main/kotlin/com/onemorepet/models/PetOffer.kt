package com.onemorepet.models

data class PetOffer(val kind: String, val species: Map<String, Double?>, val age: Int, val price: Int)