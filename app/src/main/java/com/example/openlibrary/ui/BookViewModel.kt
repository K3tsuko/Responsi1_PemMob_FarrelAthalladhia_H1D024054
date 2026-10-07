package com.example.openlibrary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlibrary.data.model.Book
import com.example.openlibrary.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository = BookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        // Load a default list so the home screen isn't empty
        searchBooks("indonesia")
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun searchBooks(keyword: String = _query.value) {
        if (keyword.isBlank()) return

        viewModelScope.launch {
            _uiState.value = BookUiState.Loading
            try {
                val books = repository.searchBooks(keyword.trim())
                _uiState.value = BookUiState.Success(books)
            } catch (e: Exception) {
                _uiState.value = BookUiState.Error(
                    e.message ?: "Something went wrong. Check your internet connection."
                )
            }
        }
    }

    // Used by the detail screen: looks up a book from the list we already have
    fun getBookByKey(key: String): Book? =
        (uiState.value as? BookUiState.Success)
            ?.books
            ?.find { it.key == key }
}
