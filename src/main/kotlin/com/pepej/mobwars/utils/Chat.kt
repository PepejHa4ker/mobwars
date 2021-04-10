package com.pepej.mobwars.utils

import com.pepej.papi.text.Text

fun String?.colorize(): String = Text.colorize(this) ?: ""
operator fun String?.unaryPlus(): String = colorize()
