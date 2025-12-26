package com.creativehustler.posbo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "iva")
    val iva: Boolean,
    @ColumnInfo(name = "permite_venta")
    val allowsSale: Boolean,
    @ColumnInfo(name = "activo")
    val active: Boolean = true
)

