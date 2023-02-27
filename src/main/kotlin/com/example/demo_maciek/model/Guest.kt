package com.example.demo_maciek.model

import com.example.demo_maciek.model.enums.Status
import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import com.gitlab.mvysny.jdbiorm.Table
import org.jdbi.v3.core.mapper.reflect.ColumnName

@Table("person")
data class Guest(
    var name: String? = null,
    var age: Int? = null,
    var status: Status? = null
) : KEntity<Long> {
    @field:ColumnName("person_id")
    override var id: Long? = null

    companion object : Dao<Guest, Long>(Guest::class.java) {}
}