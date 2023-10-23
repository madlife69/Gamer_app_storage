package tn.esprit.gamer.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tn.esprit.gamer.GameDao
import tn.esprit.gamer.UserDao
import tn.esprit.gamer.model.Product
import tn.esprit.gamer.model.User

@Database(entities = [User::class, Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun GameDao(): GameDao


    companion object {
        //TODO 11 Apply the Design Pattern singleton
        var db: AppDatabase? = null


        fun getDatabase(context: Context?=null): AppDatabase {
            if (db == null) {
                synchronized(AppDatabase::class) {
                    db = Room.databaseBuilder(
                        context!!.applicationContext,
                        AppDatabase::class.java,
                        "Sample.db"
                    )

                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!
        }
    }
}