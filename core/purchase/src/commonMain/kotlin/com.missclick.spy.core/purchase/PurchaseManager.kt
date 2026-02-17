package com.missclick.spy.core.purchase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PurchaseManager {
    val isPremium: StateFlow<Boolean>
}