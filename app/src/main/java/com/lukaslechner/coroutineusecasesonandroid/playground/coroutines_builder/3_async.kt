package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines_builder

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()

    val deferred1 = async<String> {
        val result1 = networkRequest(500, 1)
        println("result1 received $result1 after ${elapsedMillis(startTime)}ms")
        result1
    }

    val deferred2 = async<String> {
        val result2 = networkRequest(100, 2)
        println("result2 received $result2 after ${elapsedMillis(startTime)}ms")
        result2
    }

    val resultList = listOf(deferred1.await(), deferred2.await())

    println("Result list: $resultList after ${elapsedMillis(startTime)}ms")
}

private fun elapsedMillis(startTime: Long): Long {
    return System.currentTimeMillis() - startTime
}

private suspend fun networkRequest(delay: Long, number: Int): String {
    delay(delay)
    return "Result $number"
}