/*
package com.example.demo_maciek.api.person

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) {

    @GetMapping("/all")
    fun returnAll(): ResponseEntity<*> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(personService.returnAll())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR :: ${e.message}")
        }
    }

    @PostMapping("/post")
    fun postPerson(@RequestParam name: String, @RequestParam age: Int): ResponseEntity<*> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(personService.postPerson(name, age))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR :: ${e.message}")
        }
    }
}*/
