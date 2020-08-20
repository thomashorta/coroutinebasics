package com.thomashorta.coroutinebasics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thomashorta.coroutinebasics.model.Word

class WordAdapter: RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    var words: List<Word> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    class WordViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val textViewWord = item.findViewById<TextView>(R.id.tvWord)

        fun bind(word: Word) {
            textViewWord.text = word.text
        }
    }
}