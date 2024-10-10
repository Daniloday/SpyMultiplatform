package com.missclick.spy.core.data

interface DeviceRepo {
    suspend fun getCurrentLanguageCode(): String
}