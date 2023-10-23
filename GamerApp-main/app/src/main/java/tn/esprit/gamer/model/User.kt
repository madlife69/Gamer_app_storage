package tn.esprit.gamer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey val id: Int,

    val fullname :String,

    val email: String,

    val password: String
)