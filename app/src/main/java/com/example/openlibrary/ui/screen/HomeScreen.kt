package com.example.openlibrary.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.openlibrary.data.model.Book
import com.example.openlibrary.ui.BookUiState
import com.example.openlibrary.ui.BookViewModel
import com.example.openlibrary.ui.components.BookItem
import com.example.openlibrary.ui.components.BookSearchBar
import com.example.openlibrary.ui.components.ErrorView
import com.example.openlibrary.ui.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    onBookClick: (Book) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("OpenLibrary Explorer", style = MaterialTheme.typography.titleLarge)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            BookSearchBar(
                query = query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = { viewModel.searchBooks() }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = uiState) {
                    is BookUiState.Loading -> LoadingView()
                    is BookUiState.Error -> ErrorView(
                        message = state.message,
                        onRetry = { viewModel.searchBooks() }
                    )
                    is BookUiState.Success -> {
                        if (state.books.isEmpty()) {
                            Text(
                                text = "No books found",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                items(state.books) { book ->
                                    BookItem(book = book, onBookClick = onBookClick)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
