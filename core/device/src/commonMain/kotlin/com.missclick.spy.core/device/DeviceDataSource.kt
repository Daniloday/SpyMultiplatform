package com.missclick.spy.core.device

interface DeviceDataSource {
    suspend fun getCurrentLanguageCode(): String
}