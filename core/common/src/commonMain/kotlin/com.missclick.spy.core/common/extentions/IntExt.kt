package com.missclick.spy.core.common.extentions

import com.missclick.spy.core.common.getRandomByTime

fun IntRange.randomByTime() = this.random(getRandomByTime())