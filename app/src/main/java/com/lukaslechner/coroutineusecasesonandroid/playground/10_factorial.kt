package com.lukaslechner.coroutineusecasesonandroid.playground

import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {

    println("${measureTimeMillis { calculateFactorialOf(5000) }}ms")
    println("${measureTimeMillis { recursiveCalculateFactorialOf(5000) }}ms")
}

private fun recursiveCalculateFactorialOf(number: Int) : BigInteger {
    if( number < 1) {
        return BigInteger.ONE
    }
    return recursiveCalculateFactorialOf( number - 1).multiply(number.toBigInteger())
}

private fun calculateFactorialOf(number: Int) : BigInteger {
    var factorial = BigInteger.ONE

    for(i in 1 .. number) {
        factorial = factorial.multiply(i.toLong().toBigInteger())
    }
    return factorial
}