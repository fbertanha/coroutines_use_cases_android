package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
//    cancelScopeJobAlsoCancelYourChildrenTogether()
    cancellingAJobWontCancelYourSiblingsOrParents()
}

private suspend fun cancellingAJobWontCancelYourSiblingsOrParents() {

    val scope = CoroutineScope(Dispatchers.Default)

    scope.coroutineContext[Job]?.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Parent job was cancelled!")
        }
    }

    val childCoroutineJob1 = scope.launch {
        delay(800)
        println("Coroutine 1 has completed!")
    }
    childCoroutineJob1.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 was cancelled!")
        }
    }


    scope.launch {
        delay(1000)
        println("Coroutine 2 has completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 was cancelled!")
        }
    }

    delay(200)
    childCoroutineJob1.cancel()
}

private suspend fun cancelScopeJobAlsoCancelYourChildrenTogether() {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        delay(600)
        println("Coroutine 1 has completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 was cancelled!")
        }
    }


    scope.launch {
        delay(1000)
        println("Coroutine 2 has completed!")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 was cancelled!")
        }
    }

    scope.coroutineContext[Job]?.cancelAndJoin()
}