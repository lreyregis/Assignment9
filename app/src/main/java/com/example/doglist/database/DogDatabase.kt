package com.example.doglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dog::class], version = 10, exportSchema = false)
abstract class DogDatabase: RoomDatabase() {
    abstract val dogDao: DogDao
    companion object {
        @Volatile
        private var INSTANCE: DogDatabase? = null
        fun getInstance(context: Context): DogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DogDatabase::class.java,
                        "dog_database"
                    ).fallbackToDestructiveMigration()
                     .build()
                    INSTANCE = instance
                }
                return instance as DogDatabase
            }
        }
    }
}