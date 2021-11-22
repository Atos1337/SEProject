package com.onemorepet.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = arrayOf("/hello"))
class HelloController {
    @GetMapping("")
    fun hello(@RequestParam name: String) = "Hello $name from OneMorePet"
}