package com.zakshaker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zakshaker.db.dao.JokeDao
import com.zakshaker.db.entities.JokeEntity


@Database(
    entities = [
        JokeEntity::class
    ],
    version = 1,
    // export version-history of this db to $projectDir/schemas
    exportSchema = true
)
abstract class DB : RoomDatabase() {

    abstract fun favoriteJokesDao(): JokeDao

    companion object {

        private const val DB_NAME = "db"

        fun createInstance(context: Context): DB =
            Room.databaseBuilder(context, DB::class.java,
                DB_NAME
            )
                .build()

    }

}