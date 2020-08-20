package com.thomashorta.coroutinesamples

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

@ExperimentalStdlibApi
fun main() {
    lightweightThreadsExample()
}


/**
 * Basic initial examples (evolution of the same Basic example)
 */

fun coroutineBasicSleepExample() {
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    println("Hello")
    Thread.sleep(2000L)
}

fun threadBasicExample() {
    thread {
        Thread.sleep(1000L)
        println("World!")
    }

    println("Hello")
    Thread.sleep(2000L)
}

fun coroutineBasicDelayExample() {
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    println("Hello")
    runBlocking {
        delay(2000L)
    }
}

fun coroutineBasicBlockingExample() {
    runBlocking {
        GlobalScope.launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
        delay(2000L)
    }
}

fun coroutineBasicJoinExample() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
        job.join()
    }
}

fun coroutineBasicStructuredConcurrencyExample() {
    runBlocking {
        launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
    }
}

fun coroutineBasicSuspendExample() {
    runBlocking {
        launch {
            printWorldDelayed()
        }

        println("Hello")
    }
}

suspend fun printWorldDelayed() {
    delay(1000L)
    println("World!")
}


/**
 * Context elements example
 */

@ExperimentalStdlibApi
fun contextElementExample() {
    runBlocking {
        printElements("runBlocking")

        launch { printElements("launch") }

        launch(Dispatchers.Default + CoroutineName("CustomCoroutine")) {
            printElements("launch (custom context)")
        }

        val job = GlobalScope.launch {
            printElements("launch (GlobalScope)")
        }

        println("This is the launch (GlobalScope) job: $job")

        job.join()
    }
}

//@ExperimentalStdlibApi
//suspend fun printElements(tag: String = "") {
//    println("$tag: Running on coroutine ${coroutineContext[CoroutineName]?.name}, on ${coroutineContext[CoroutineDispatcher]}, this is my job: ${coroutineContext[Job]}")
//}


/**
 * Dispatchers example
 */
fun dispatchersSingleThreadExample() {
    runBlocking {
        launch {
            Thread.sleep(2000L)
            println("Finished blocking operation 1")
        }

        launch {
            Thread.sleep(2000L)
            println("Finished blocking operation 2")
        }

        delay(100L)
        println("Got here!")
    }
}

fun dispatchersMultiThreadExample() {
    runBlocking {
        launch {
            delay(2000L)
            println("Finished blocking operation 1")
        }

        launch {
            delay(2000L)
            println("Finished blocking operation 2")
        }

        delay(100L)
        println("Got here!")
    }
}

fun unconfinedDispatcherExample() {
    runBlocking {
        println("runBlocking: running on thread ${Thread.currentThread().name}")

        launch(Dispatchers.Default) {
            println("launchDefault: running on thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("launchUnconfined: running on thread ${Thread.currentThread().name}")

            launch(Dispatchers.Default) {
                println("innerLaunchDefault: running on thread ${Thread.currentThread().name}")
            }

            delay(100L)
            println("launchUnconfined: now running on thread ${Thread.currentThread().name}")
        }
    }
}


/**
 * Async and Await (Deferred)
 */
suspend fun calculateSomethingA(): Int {
    delay(1000L)
    return 13
}

suspend fun calculateSomethingB(): Int {
    delay(1000L)
    return 29
}

fun concurrentWorkBadExample() {
    runBlocking {
        val elapsed = measureTimeMillis {
            val a = calculateSomethingA()
            val b = calculateSomethingB()

            println("Result = ${a + b}")
        }

        println("Elapsed time = $elapsed ms")
    }
}

fun concurrentAsyncExample() {
    runBlocking {
        val elapsed = measureTimeMillis {
            val a = async {
                calculateSomethingA()
            }
            val b = async {
                calculateSomethingB()
            }

            println("Result = ${a.await() + b.await()}")
        }

        println("Elapsed time = $elapsed ms")
    }
}


/**
 * withContext + suspend example
 */

fun coroutineWithContextExample() {
    runBlocking {
        val job = launch {
            doWork(1)
        }

        launch {
            while (!job.isCompleted) {
                delay(250L)
                println(".")
                delay(250L)
            }
        }

        println("Inside runBlocking!")
    }
}

suspend fun doWork(id: Int): Unit = withContext(Dispatchers.Default) {
    println("Starting work $id!")
    Thread.sleep(2000L)
    println("Finished work $id!")
}


/**
 * Demo of heavy threads and lightweight coroutines
 */

fun heavyThreadsExample() {
    // note: this might work depending on the computer, jvm memory, and number of threads spawned.
    // To demonstrate better, run at: https://play.kotlinlang.org/
    repeat(100_000) {
        thread {
            Thread.sleep(5000L)
            print(".")
        }
    }
}

fun lightweightThreadsExample() {
    runBlocking {
        repeat(100_000) {
            launch {
                delay(2000L)
                print(".")
            }
        }
    }
}