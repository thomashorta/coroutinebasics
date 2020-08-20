package com.thomashorta.coroutinebasics.data

import com.thomashorta.coroutinebasics.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(
    private val service: FakeWordService,
    private val requestDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getWords(): List<Word> = withContext(requestDispatcher) {
        service.getWords().map { Word(it) }
    }
}