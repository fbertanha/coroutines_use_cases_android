package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.*

/**
 * Created by felipebertanha on 05/January/2022
 */
fun main() = runBlocking {

    println("main starts")
    joinAll(
        async { suspendingCoroutine(1, 500) },
        async { suspendingCoroutine(2, 300) },
        async {
              repeat(5) { it ->
                  println("task {${it}} is working on ${Thread.currentThread().name}")
                  delay(100)
              }
        },
    )
}

suspend fun suspendingCoroutine(number: Int, delay: Long) {
    println("Coroutine {$number} starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine {$number} has finished on ${Thread.currentThread().name}")
}
