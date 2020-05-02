package com.zakshaker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zakshaker.db.entities.JokeEntity

@Dao
interface JokeDao {
    @Query("SELECT * FROM favorite_jokes")
    suspend fun getAll(): List<JokeEntity>

    @Query("SELECT * FROM favorite_jokes WHERE id IN (:jokeIds)")
    suspend fun loadAllByIds(jokeIds: IntArray): List<JokeEntity>

    @Query(
        "SELECT * FROM favorite_jokes WHERE text LIKE '%' || :text || '%'"
    )
    suspend fun findByText(text: String): List<JokeEntity>

    @Insert
    suspend fun insertAll(vararg jokes: JokeEntity): List<Long>

    @Query(
        "DELETE FROM favorite_jokes WHERE id IN (:jokeIds)"
    )
    suspend fun deleteByIds(jokeIds: IntArray)
}