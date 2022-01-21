package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines_builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()

    val resultList = mutableListOf<String>()

    val job1 = launch {
        val result1 = networkRequest(100, 1)
        resultList.add(result1)
        println("result1 received $result1 after ${elapsedMillis(startTime)}ms")
    }

    val job2 = launch {
        val result2 = networkRequest(500, 2)
        resultList.add(result2)
        println("result2 received $result2 after ${elapsedMillis(startTime)}ms")
    }

    //needs to call the .join here, otherwise the println below will be called before the launch calls above
    job1.join()
    job2.join()

    println("Result list: $resultList after ${elapsedMillis(startTime)}ms")
}

private fun elapsedMillis(startTime: Long) : Long {
    return System.currentTimeMillis() - startTime
}

private suspend fun networkRequest(delay: Long, number : Int) : String {
    delay(delay)
    return "Result $number"
}