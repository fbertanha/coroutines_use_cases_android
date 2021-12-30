package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        try {
            viewModelScope.launch {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                val mostRecent = recentAndroidVersions.last()
                val featuresOnMostRecentVersion = mockApi.getAndroidVersionFeatures(mostRecent.apiLevel)

                uiState.value = UiState.Success(featuresOnMostRecentVersion)
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("Network request failed!")
        }
    }
}