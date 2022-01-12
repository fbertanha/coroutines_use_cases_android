package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

/**
 * Created by felipebertanha on 05/January/2022
 */
fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { coroutineWithThreadInfo(1, 500) },
        async { coroutineWithThreadInfo(2, 300) }
    )
}

suspend fun coroutineWithThreadInfo(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}
