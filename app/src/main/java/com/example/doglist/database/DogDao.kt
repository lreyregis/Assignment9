package com.example.doglist.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DogDao {
    @Insert
    suspend fun insert(dog:Dog)

    @Update
    suspend fun update(dog:Dog)

    @Delete
    suspend fun delete(dog:Dog)

    @Query("DELETE FROM dog_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM dog_table WHERE id = :key")
    suspend fun get(key:Int): Dog

    @Query("SELECT * FROM dog_table WHERE breed = :key")
    suspend fun getByBreed(key:String): Dog

    @Query("SELECT * FROM dog_table ORDER BY breed ASC")
    suspend fun getAll(): MutableList<Dog>

}