package com.rafi.quoteapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rafi.quoteapp.data.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: QuoteRepository): ViewModel() {

    fun getQuote() = repository.getRandomQuotes().asLiveData(Dispatchers.IO)
}