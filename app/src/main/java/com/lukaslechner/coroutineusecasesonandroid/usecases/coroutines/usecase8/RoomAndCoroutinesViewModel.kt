package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb

        viewModelScope.launch {
            //Local data
            val localVersions = database.getAndroidVersions()

            if (localVersions.isNotEmpty()) {
                uiState.value =
                    UiState.Success(DataSource.DATABASE, localVersions.mapToUiModelList())
            } else {
                uiState.value = UiState.Error(DataSource.DATABASE, "Database empty")
            }

            //Network call
            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val networkVersions = api.getRecentAndroidVersions()
                networkVersions.forEach { androidVersion -> database.insert(androidVersion.mapToEntity()) }

                uiState.value = UiState.Success(DataSource.NETWORK, networkVersions)
            } catch (e: Exception) {
                uiState.value = UiState.Error(DataSource.NETWORK, "Network request failed!")
            }

        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            database.clear()
        }
    }
}

enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}