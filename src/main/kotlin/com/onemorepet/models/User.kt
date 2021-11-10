package com.onemorepet.models

data class User(
    val name: String,
    val location: Location,
    val discounts: MutableList<Discount>? = null
)