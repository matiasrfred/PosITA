package com.creativehustler.posbo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    @ColumnInfo(name = "department_id")
    val departmentId: Long,
    val active: Boolean = true
)

