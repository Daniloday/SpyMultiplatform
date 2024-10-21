package com.missclick.spy.core.data.repo

import com.missclick.spy.core.data.AppStoreRepo
import com.missclick.spy.core.device.DeviceDataSource

internal class AppStoreRepoImpl(
    private val deviceDataSource: DeviceDataSource
) : AppStoreRepo {

    override suspend fun requestRateUs() {
        deviceDataSource.requestRateUs()
    }
    
}