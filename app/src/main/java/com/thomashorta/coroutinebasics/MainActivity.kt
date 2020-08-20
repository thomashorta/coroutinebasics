package com.thomashorta.coroutinebasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.thomashorta.coroutinebasics.model.Word
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_message.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val wordViewModel: WordViewModel by viewModel()
    private val adapter: WordAdapter = WordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupErrorButton()
        setupObservers()
        wordViewModel.fetchWords()
    }

    private fun setupRecyclerView() {
        rvContent.adapter = adapter
    }

    private fun setupErrorButton() {
        buttonError.setOnClickListener {
            wordViewModel.fetchWords()
        }
    }

    private fun setupObservers() {
        wordViewModel.viewStateLiveData.observe(this, Observer {
            when (it) {
                WordViewModel.ViewState.Loading -> handleLoading()
                is WordViewModel.ViewState.Success -> handleSuccess(it.words)
                is WordViewModel.ViewState.Error -> handleError(it.message)
            }
        })
    }

    private fun handleLoading() {
        pbLoading.visibility = View.VISIBLE
        rvContent.visibility = View.GONE
        layoutError.visibility = View.GONE
    }

    private fun handleSuccess(words: List<Word>) {
        adapter.words = words

        pbLoading.visibility = View.GONE
        rvContent.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    private fun handleError(message: String) {
        tvError.text = message

        pbLoading.visibility = View.GONE
        rvContent.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
    }
}