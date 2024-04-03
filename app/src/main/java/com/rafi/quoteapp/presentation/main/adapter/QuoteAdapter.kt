package com.rafi.quoteapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafi.quoteapp.data.model.Quote
import com.rafi.quoteapp.databinding.ListQuoteBinding

class QuoteAdapter(private val itemClick: (Quote) -> Unit) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    fun submitData(data: List<Quote>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ListQuoteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return QuoteViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bindView(asyncDataDiffer.currentList[position])
    }

    class QuoteViewHolder (private val binding : ListQuoteBinding, val itemClick: (Quote) -> Unit): RecyclerView.ViewHolder(binding.root){
        fun bindView(item: Quote){
            with(item){
                binding.tvCharacterFill.text = item.character
                binding.tvAnimeFill.text = item.anime
                binding.tvQuoteFill.text = item.quotes
                itemView.setOnClickListener{ itemClick(this) }
            }
        }
    }
}