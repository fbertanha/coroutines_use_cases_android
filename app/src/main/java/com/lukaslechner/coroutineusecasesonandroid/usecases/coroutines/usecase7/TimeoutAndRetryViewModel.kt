package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        val numberOfRetries = 2
        val timeout = 1000L

        val pieFeaturesDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }
        val oreoFeaturesDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = awaitAll(pieFeaturesDeferred, oreoFeaturesDeferred)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Something went wrong. Try again later!")
            }
        }
    }
}

private suspend fun <T> retryWithTimeout(
    numberOfRetries: Int,
    timeoutInMillis: Long,
    block: suspend () -> T
): T {
    return retry(numberOfRetries) {
        withTimeout(timeoutInMillis) { block() }
    }
}

private suspend fun <T> retry(
    numberOfRetries: Int,
    delayBetweenRetries: Long = 100,
    block: suspend () -> T
): T {
    repeat(numberOfRetries) {
        try {
            return block()
        } catch (e: Exception) {
            Timber.e(e)
        }
        delay(delayBetweenRetries)
    }
    return block()
}