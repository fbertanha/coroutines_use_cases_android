package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlin.concurrent.thread

/**
 * Created by felipebertanha on 10/January/2022
 */

fun main() {
    repeat(times = 1_000_000) {
        thread {
            Thread.sleep(5000)
            print(".")
        }
    }
}