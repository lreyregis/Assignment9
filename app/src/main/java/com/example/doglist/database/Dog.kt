package com.example.doglist.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doglist.R
import org.jetbrains.annotations.NotNull

import java.sql.Blob

@Entity(tableName = "dog_table")
data class Dog(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @NotNull
    @ColumnInfo(name = "breed", collate = ColumnInfo.NOCASE)
    var breed: String = "",

    @NotNull
    @ColumnInfo(name = "name")
    var name: String = "",

    @NotNull
    @ColumnInfo(name = "age")
    var age: Int = 0,

    @ColumnInfo(name="image_drawable")
    var imageName: Int = R.drawable.dogpaw,

    @ColumnInfo(name="image_bitmap")
    var bitMapName: ByteArray? = null
)
