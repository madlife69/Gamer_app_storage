package tn.esprit.gamer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val description: String
)