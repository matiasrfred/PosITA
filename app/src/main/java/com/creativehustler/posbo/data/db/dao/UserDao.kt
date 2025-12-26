package com.creativehustler.posbo.data.db.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.creativehustler.posbo.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Query("""
        SELECT * FROM users 
        WHERE username = :user 
        AND password = :pass 
        AND active = 1
        LIMIT 1
    """)
    fun login(user: String, pass: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users")
    fun countUsers(): Int

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): List<UserEntity>
}
