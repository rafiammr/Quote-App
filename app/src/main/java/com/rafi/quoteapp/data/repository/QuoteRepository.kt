package com.rafi.quoteapp.data.repository

import com.rafi.quoteapp.data.datasource.QuoteDataSource
import com.rafi.quoteapp.data.model.Quote
import com.rafi.quoteapp.data.toQuotes
import com.rafi.quoteapp.utils.ResultWrapper
import com.rafi.quoteapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>>
}

class QuoteRepositoryImpl(private val dataSource: QuoteDataSource) : QuoteRepository{
    override fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>> {
        return proceedFlow { dataSource.getRandomQuotes().toQuotes() }
    }

}