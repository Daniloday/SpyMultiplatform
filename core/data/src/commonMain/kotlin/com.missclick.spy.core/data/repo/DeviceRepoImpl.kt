package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.DeviceRepo
import com.missclick.spy.core.device.DeviceDataSource

internal class DeviceRepoImpl(
    private val deviceDataSource: DeviceDataSource
): DeviceRepo {
    override suspend fun getCurrentLanguageCode(): String {
        return deviceDataSource.getCurrentLanguageCode()
    }

}