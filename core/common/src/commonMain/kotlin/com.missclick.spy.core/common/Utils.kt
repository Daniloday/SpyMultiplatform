package com.missclick.spy.core.common

import kotlin.random.Random

fun getRandomByTime(): Random = Random(kotlin.time.Clock.System.now().toEpochMilliseconds())