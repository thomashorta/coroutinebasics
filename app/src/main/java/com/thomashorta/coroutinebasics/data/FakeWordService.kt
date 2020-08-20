package com.thomashorta.coroutinebasics.data

import kotlinx.coroutines.delay

class FakeWordService {
    suspend fun getWords(): List<String> {
        delay(3000L)
        return WORDS_LIST
    }

    companion object {
        private val WORDS_LIST = listOf(
            "Delta",
            "Echo",
            "Foxtrot",
            "Golf",
            "Alfa",
            "Bravo",
            "Charlie",
            "Lima",
            "Mike",
            "November",
            "Oscar",
            "Papa",
            "Hotel",
            "India",
            "Juliett",
            "Kilo",
            "Uniform",
            "Victor",
            "Whiskey",
            "X-ray",
            "Quebec",
            "Romeo",
            "Sierra",
            "Quebec", // duplicate
            "Romeo", // duplicate
            "Sierra", // duplicate
            "Tango",
            "Yankee",
            "Zulu"
        )
    }
}