package com.creativehustler.posbo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.creativehustler.posbo.data.db.entity.DepartmentEntity

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM departments ORDER BY name")
    fun getAll(): List<DepartmentEntity>

    @Insert
    fun insert(department: DepartmentEntity): Long

    @Update
    fun update(department: DepartmentEntity)

    @Delete
    fun delete(department: DepartmentEntity)
}

