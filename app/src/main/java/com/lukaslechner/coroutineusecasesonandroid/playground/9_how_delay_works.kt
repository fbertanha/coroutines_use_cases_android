package com.lukaslechner.coroutineusecasesonandroid.playground

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

/**
 * Created by felipebertanha on 05/January/2022
 */
fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { delayDemonstration(1, 500) },
        async { delayDemonstration(2, 300) }
    )
}

suspend fun delayDemonstration(number: Int, delay: Long) {
    println("Coroutine $number starts work")
    Handler(Looper.getMainLooper())
        .postDelayed({
            println("Coroutine $number has finished")
        }, delay)

}
