package com.goander.dictionary.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.goander.dictionary.database.entity.BookmarkedWordEntity

@Dao
interface BookmarkedWordDao {

    @Insert
    public suspend fun insertBookmarkedWord(bookmarkedWordEntity: BookmarkedWordEntity): Long

    @Query("DELETE FROM bookmarked_words WHERE word = :word")
    public suspend fun deleteBookmarkedWord(word: String): Int

    @Transaction
    public suspend fun insertIfNotExistsDeleteIfExists(word: String): Long {
        // if there is no bookmarked word to delete than insert the word
        // We check whether any row has been deleted by
        // checking the number returned with the delete operation.
        if (deleteBookmarkedWord(word) == 0)
            return insertBookmarkedWord(BookmarkedWordEntity(word = word))
        // return 0 if the word was exist and deleted
        return 0
    }

    @Query("SELECT * FROM bookmarked_words WHERE word LIKE '%' || TRIM(:search) || '%' ORDER BY word")
    public fun getBookmarkedWordsBySearchPagingSource(search: String): PagingSource<Int, BookmarkedWordEntity>
}