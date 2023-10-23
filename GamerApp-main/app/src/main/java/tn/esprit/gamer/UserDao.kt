package tn.esprit.gamer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tn.esprit.gamer.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)


    @Query("SELECT COUNT(*) FROM User WHERE email = :email")
    fun doesEventExist(email: String): Int

    @Query("SELECT COUNT(*) FROM User WHERE email = :email and password = :password")
    fun verifUser(email: String,password: String): Int





}