package com.onemorepet.models

data class PetOffer(val kind: String, val species: List<Pair<String, Double>>, val age: Int, val price: Int)
