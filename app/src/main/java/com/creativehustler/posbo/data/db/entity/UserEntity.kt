package com.creativehustler.posbo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val firstName: String = "",
    val lastName: String = "",

    val username: String,
    val password: String, // luego puedes hashear
    val role: String,     // superadmin, admin, supervisor, cajero
    val active: Boolean = true
)
