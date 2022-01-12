package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by felipebertanha on 10/January/2022
 */

fun main() = runBlocking<Unit> {
    repeat(times = 1_0) {
        launch() {
            delay(5000)
            print(".")
        }
    }
}