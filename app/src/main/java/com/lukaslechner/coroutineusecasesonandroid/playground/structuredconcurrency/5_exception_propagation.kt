package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking<Unit>{
    ordinaryJob()
    supervisorJob()
}

/**
 * SupervisorJob and it's children aren't cancelled if any child coroutine fails, it's keep active.
 * ViewModelScope use this type of job
 * */
private suspend fun supervisorJob() {

    println("**** Start - Supervisor Job Sample ****")
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception: $throwable")
    }

    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(50)
        println("Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 got cancelled!")
        }
    }

    Thread.sleep(1000)

    println("Scope got cancelled: ${!scope.isActive}")

    println("**** End - Supervisor Job Sample ****")
}


/**
 * Ordinary Job and it's children are cancelled if any child coroutine fails
 * */
private suspend fun ordinaryJob() {

    println("**** Start - Ordinary Job Sample ****")
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(500)
        println("Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 got cancelled!")
        }
    }

    scope.coroutineContext[Job]!!.join()

    println("Scope got cancelled: ${!scope.isActive}")

    println("**** End - Ordinary Job Sample ****")
}