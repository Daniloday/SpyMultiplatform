package com.missclick.spy.core.common

import kotlin.random.Random
import kotlinx.datetime.Clock

fun getRandomByTime(): Random = Random(Clock.System.now().toEpochMilliseconds())