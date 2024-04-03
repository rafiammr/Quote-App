package com.rafi.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.rafi.quoteapp.R
import com.rafi.quoteapp.data.datasource.QuoteApiDataSource
import com.rafi.quoteapp.data.datasource.QuoteDataSource
import com.rafi.quoteapp.data.repository.QuoteRepository
import com.rafi.quoteapp.data.repository.QuoteRepositoryImpl
import com.rafi.quoteapp.data.source.network.services.QuoteApiServices
import com.rafi.quoteapp.databinding.ActivityMainBinding
import com.rafi.quoteapp.presentation.main.adapter.QuoteAdapter
import com.rafi.quoteapp.utils.GenericViewModelFactory
import com.rafi.quoteapp.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    private val quoteAdapter: QuoteAdapter by lazy {
        QuoteAdapter {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        bindListQuote()
    }

    private fun setupList() {
        binding.rvQuote.adapter = quoteAdapter
    }

    private fun bindListQuote() {
        viewModel.getQuote().observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvQuote.isVisible = true
                    result.payload?.let { quote ->
                        quoteAdapter.submitData(quote)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvQuote.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvQuote.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_no_data)
                    binding.rvQuote.isVisible = false
                }

            )
        }
    }


}