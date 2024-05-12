package com.example.todoapplication.constant

import java.util.concurrent.TimeUnit

object Util {
    const val TIME_COUNTDOWN: Long = 3000L
    private const val TIME_FORMAT = "%02d:%02d"

    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}