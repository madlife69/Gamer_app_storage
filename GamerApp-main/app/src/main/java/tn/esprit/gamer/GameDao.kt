package tn.esprit.gamer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tn.esprit.gamer.model.Product
import tn.esprit.gamer.model.User

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("SELECT COUNT(*) FROM Product WHERE id = :id")
    fun doesproductExist(id: Int): Int

    @Delete
    fun deleteEvent(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllEvents(): MutableList<Product>

}