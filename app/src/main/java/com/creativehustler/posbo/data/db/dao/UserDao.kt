package com.creativehustler.posbo.data.db.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.creativehustler.posbo.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Query("""
        SELECT * FROM users 
        WHERE username = :username 
        AND password = :password 
        AND active = 1
        LIMIT 1
    """)
    fun login(username: String, password: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users")
    fun countUsers(): Int
}
