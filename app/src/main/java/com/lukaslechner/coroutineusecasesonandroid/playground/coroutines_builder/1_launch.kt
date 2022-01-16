package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines_builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    val job = launch {
        networkRequest()
        println("result received")
    }
    job.join()
    println("end of runBlocking")
}

suspend fun networkRequest() : String {
    delay(500)
    return "Result"
}