package com.rafi.quoteapp.data

import com.rafi.quoteapp.data.model.Quote
import com.rafi.quoteapp.data.source.network.model.QuoteResponse

fun QuoteResponse.toQuote() = Quote(
    id = this.id.orEmpty(),
    quotes = this.quotes.orEmpty(),
    anime = this.anime.orEmpty(),
    character = this.character.orEmpty()
)

fun Collection<QuoteResponse>.toQuotes() = this.map {
    it.toQuote()
}