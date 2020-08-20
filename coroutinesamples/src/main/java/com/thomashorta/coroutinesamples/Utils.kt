package com.thomashorta.coroutinesamples

import kotlinx.coroutines.newSingleThreadContext

val singleThreadContext = newSingleThreadContext("ExampleContext")

fun log(message: String) {
    println("${Thread.currentThread().name}: $message")
}