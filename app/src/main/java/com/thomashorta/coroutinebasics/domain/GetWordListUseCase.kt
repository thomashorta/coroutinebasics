package com.thomashorta.coroutinebasics.domain

import com.thomashorta.coroutinebasics.data.WordRepository
import com.thomashorta.coroutinebasics.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetWordListUseCase(
    private val wordRepository: WordRepository,
    private val workDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(): List<Word> = withContext(workDispatcher) {
        val rawWordList = wordRepository.getWords()

        delay(2000L) // simulate heavy computation with list
        rawWordList
            .filter { word -> rawWordList.count { it.text == word.text } == 1 }
            .sortedBy { it.text }

    }
}