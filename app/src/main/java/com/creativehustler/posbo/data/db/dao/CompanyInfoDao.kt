package com.creativehustler.posbo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.creativehustler.posbo.data.db.entity.CompanyInfoEntity

@Dao
interface CompanyInfoDao {
    @Query("SELECT * FROM company_info LIMIT 1")
    fun getConfig(): CompanyInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(info: CompanyInfoEntity)
}

