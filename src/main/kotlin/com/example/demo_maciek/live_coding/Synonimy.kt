package com.example.demo_maciek.live_coding

import com.github.vokorm.KEntity
import org.jdbi.v3.core.mapper.reflect.ColumnName
import com.gitlab.mvysny.jdbiorm.Dao
import com.gitlab.mvysny.jdbiorm.Table

@Table("synonimy")
class Synonimy(
    @field:ColumnName("syn_id")
    override var id: Long? = null
) : KEntity<Long> {
    var syn_tags: Array<String>? = null
    var syn_from: String? = null
    var syn_to: String? = null

    companion object : Dao<Synonimy, Long>(Synonimy::class.java) {

    }
}