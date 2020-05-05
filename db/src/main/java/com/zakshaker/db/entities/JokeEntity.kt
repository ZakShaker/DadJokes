package com.zakshaker.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_jokes"
)
data class JokeEntity(
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "text")
    val text: String
)