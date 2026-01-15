package com.creativehustler.posbo.data.db.entity

import androidx.room.ColumnInfo

data class SubcategoryWithCategory(
    val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "category_name")
    val categoryName: String?,
    val active: Boolean
)

