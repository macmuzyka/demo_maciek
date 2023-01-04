package com.example.demo_maciek.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCtrl {
    @GetMapping("/api/dane")
    fun dane(): List<Row> {
        return listOf(
            Row(1),
            Row(2),
        )
    }
}

data class Row(val id: Long)