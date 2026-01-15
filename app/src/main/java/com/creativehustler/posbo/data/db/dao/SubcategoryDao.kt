package com.creativehustler.posbo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.creativehustler.posbo.data.db.entity.SubcategoryEntity
import com.creativehustler.posbo.data.db.entity.SubcategoryWithCategory

@Dao
interface SubcategoryDao {

    @Query("""
        SELECT s.id, s.name, s.description, s.category_id, c.name AS category_name, s.active
        FROM subcategories s
        LEFT JOIN categories c ON c.id = s.category_id
        ORDER BY s.name
    """)
    fun getAllWithCategory(): List<SubcategoryWithCategory>

    @Query("SELECT * FROM subcategories ORDER BY name")
    fun getAll(): List<SubcategoryEntity>

    @Insert
    fun insert(subcategory: SubcategoryEntity): Long

    @Update
    fun update(subcategory: SubcategoryEntity)

    @Delete
    fun delete(subcategory: SubcategoryEntity)
}

