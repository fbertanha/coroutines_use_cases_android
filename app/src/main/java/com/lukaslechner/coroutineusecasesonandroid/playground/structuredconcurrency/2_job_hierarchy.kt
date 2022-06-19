package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {
//    sample1()

    sample2()
}

private fun sample2() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    // We should avoid passing a job to this coroutine builder (scope.launch(newJobRef),
    // because it's override the parents job, broking the hierarchy
    val passedJob = Job()
    val coroutineJob = scope.launch(passedJob) {

        println("Starting coroutine")
        delay(1000)
    }

    Thread.sleep(100)

    println("Is coroutineJob a child of scopeJob => ${scopeJob.children.contains(coroutineJob)}")
    // output: Is coroutineJob a child of scopeJob => false
    println("Is coroutineJob a child of passedJob => ${passedJob.children.contains(coroutineJob)}")
    // output: Is coroutineJob a child of scopeJob => true
}

private fun sample1() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    var childCoroutineJob: Job? = null
    val coroutineJob = scope.launch {

        childCoroutineJob = launch {
            println("Starting child coroutine")
            delay(1000)
        }
        println("Starting coroutine")
        delay(1000)
    }

    Thread.sleep(100)

    println("Is coroutineJob a child of scopeJob => ${scopeJob.children.contains(coroutineJob)}")
    // output: Is coroutineJob a child of scopeJob => true
    println("Is childCoroutineJob a child of coroutineJob => ${coroutineJob.children.contains(childCoroutineJob)}")
    // output : Is childCoroutineJob a child of coroutineJob => true
}