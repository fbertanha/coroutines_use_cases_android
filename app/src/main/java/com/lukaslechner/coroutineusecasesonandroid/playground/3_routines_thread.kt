package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlin.concurrent.thread

/**
 * Created by felipebertanha on 05/January/2022
 */


fun main() {
    println("main starts")
    threadRoutine(1, 500)
    threadRoutine(2, 300)
    Thread.sleep(1000)
    println("main ends")
}

fun threadRoutine(number: Int, delay: Long) {
    thread {
        println("Routine $number starts work")
        Thread.sleep(delay)
        println("Routine $number has finished")
    }

}