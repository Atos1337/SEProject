package com.onemorepet.models

data class Discount(
    val value: Double,
    val kinds: MutableList<String?>,
)