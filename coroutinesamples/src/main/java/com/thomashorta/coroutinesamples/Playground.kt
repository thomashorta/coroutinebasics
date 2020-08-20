package com.thomashorta.coroutinesamples

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

@ExperimentalStdlibApi
fun main() {
    runBlocking {
        printElements("runBlocking")

        launch {
            printElements("launch")
        }

        launch (Dispatchers.Default + CoroutineName("CustomName")) {
            printElements("launch(Custom)")
        }

        val job = GlobalScope.launch {
            printElements("launch(GlobalScope)")
        }

        job.join()
    }
}

@ExperimentalStdlibApi
suspend fun printElements(tag: String = "") {
    println("$tag: Running on coroutine ${coroutineContext[CoroutineName]?.name}, on ${coroutineContext[CoroutineDispatcher]}, this is my job: ${coroutineContext[Job]}")
}