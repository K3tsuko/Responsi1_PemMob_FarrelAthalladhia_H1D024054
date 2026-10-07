package com.example.openlibrary.data.repository

import com.example.openlibrary.data.model.Book
import com.example.openlibrary.data.remote.OpenLibraryApi
import com.example.openlibrary.data.remote.RetrofitInstance

class BookRepository(
    private val api: OpenLibraryApi = RetrofitInstance.api
) {
    suspend fun searchBooks(query: String): List<Book> =
        api.searchBooks(query)
            .docs
            .orEmpty()
            .filter { it.key != null && it.title != null }
}