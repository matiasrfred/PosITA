package com.creativehustler.posbo.data.db.entity

import androidx.room.ColumnInfo

data class CategoryWithDepartment(
    val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo(name = "department_id")
    val departmentId: Long,
    @ColumnInfo(name = "department_name")
    val departmentName: String?,
    val active: Boolean
)

