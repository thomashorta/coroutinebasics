package com.thomashorta.coroutinesamples

import kotlinx.coroutines.*

fun main() {
//    coroutineScopeBuilderExample()
//    coroutineScopeBuilderBlockingExample()
//    coroutineCancellationExample()
    coroutineFailureExample()
}

fun coroutineScopeBuilderExample() {
    // to better see differences of runBlocking and coroutineScope we need a single threaded context
    val singleThreadContext = newSingleThreadContext("MyContext")

    runBlocking(singleThreadContext) {
        launch {
            delay(200L)
            log("Task from runBlocking child")
        }

        coroutineScope {
            launch {
                delay(500L)
                log("Task from coroutineScope child")
            }

            delay(100L)
            log("Task from coroutineScope")
        }

        log("Example is over!")
    }
}

fun coroutineScopeBuilderBlockingExample() {
    // to better see differences of runBlocking and coroutineScope we need a single threaded context
    val singleThreadContext = newSingleThreadContext("MyContext")

    runBlocking(singleThreadContext) {
        launch {
            delay(200L)
            log("Task from runBlocking child")
        }

        runBlocking {
            launch {
                delay(500L)
                log("Task from coroutineScope child")
            }

            delay(100L)
            log("Task from coroutineScope")
        }

        log("Example is over!")
    }
}

/**
 * Examples with cancellation and failure
 */
fun coroutineCancellationExample() {
    runBlocking {
        val job1 = launch { heavyCancelableComputation(1) }
        val job2 = launch { heavyCancelableComputation(2) }

        delay(200L)
        println("Cancel computation #1")
        job1.cancel()
    }
}

fun coroutineFailureExample() {
    try {
        runBlocking {
            launch { heavyBlockingComputation(1) }
            launch { failingComputation(2) }

            delay(200L)
        }
    } catch (e: Exception) {
        println("runBlocking failed with ${e.message}")
    }
}

suspend fun heavyBlockingComputation(id: Int) = withContext(Dispatchers.Default) {
    println("Computation #$id started")
    repeat(10) {
        Thread.sleep(200L)
    }
    println("Computation #$id finished")
}

suspend fun heavySuspendableComputation(id: Int) = withContext(Dispatchers.Default) {
    println("Computation #$id started")
    repeat(10) {
        delay(200L)
    }
    println("Computation #$id finished")
}

suspend fun heavyCancelableComputation(id: Int) = withContext(Dispatchers.Default) {
    println("Computation #$id started")
    repeat(10) {
        Thread.sleep(200L)
        if (!isActive) {
            println("Computation #$id got cancelled")
            throw CancellationException()
        }
    }
    println("Computation #$id finished")
}

suspend fun failingComputation(id: Int): Unit = withContext(Dispatchers.Default) {
    println("Computation #$id started")
    delay(150L)
    println("Computation #$id failed")
    throw Exception("Failing computation #$id failed")
    println("Computation #$id finished")
}