package com.missclick.spy.core.common.extentions

import com.missclick.spy.core.common.getRandomByTime

fun Iterable<Any>.shuffledByTime() = this.shuffled(getRandomByTime())
