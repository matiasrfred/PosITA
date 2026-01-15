package com.creativehustler.posbo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.creativehustler.posbo.data.db.entity.CategoryEntity
import com.creativehustler.posbo.data.db.entity.CategoryWithDepartment

@Dao
interface CategoryDao {

    @Query("""
        SELECT c.id, c.name, c.description, c.department_id, d.name AS department_name, c.active
        FROM categories c
        LEFT JOIN departments d ON d.id = c.department_id
        ORDER BY c.name
    """)
    fun getAllWithDepartment(): List<CategoryWithDepartment>

    @Query("SELECT * FROM categories ORDER BY name")
    fun getAll(): List<CategoryEntity>

    @Insert
    fun insert(category: CategoryEntity): Long

    @Update
    fun update(category: CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)
}

