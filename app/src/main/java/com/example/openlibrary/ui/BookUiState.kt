package com.example.openlibrary.ui

import com.example.openlibrary.data.model.Book

sealed class BookUiState {
    object Loading : BookUiState()
    data class Success(val books: List<Book>) : BookUiState()
    data class Error(val message: String) : BookUiState()
}
