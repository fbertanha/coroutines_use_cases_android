package com.lukaslechner.coroutineusecasesonandroid.playground

/**
 * Created by felipebertanha on 05/January/2022
 */


fun main() {
    println("main starts")
    routine(1, 500)
    routine(2, 300)
    println("main ends")
}

fun routine(number: Int, delay: Long) {
    println("Routine $number starts work")
    Thread.sleep(delay)
}