package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.max

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            val numberOfRetries = 2

            try {
                retry(numberOfRetries) {
                    loadRecentAndroidVersions()
                }
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        initialDelayInMillis: Long = 100,
        maxDelayInMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayInMillis
        repeat(numberOfRetries) {
            try {
                return block.invoke()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayInMillis)
        }
        return block.invoke()
    }

    private suspend fun loadRecentAndroidVersions() {
        val recentVersions = api.getRecentAndroidVersions()
        uiState.value = UiState.Success(recentVersions)
    }

}