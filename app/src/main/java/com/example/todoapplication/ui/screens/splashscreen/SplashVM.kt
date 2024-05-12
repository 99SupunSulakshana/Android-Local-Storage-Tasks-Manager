package com.example.todoapplication.ui.screens.splashscreen

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.constant.Util
import com.example.todoapplication.constant.Util.formatTime

class SplashVM(
    application: Application
): AndroidViewModel(application) {

    private var countDownTimer: CountDownTimer? = null
    private val _complete = MutableLiveData<Boolean>(false)
    val complete: LiveData<Boolean> get() = _complete

    init {
        countDownTimer = object : CountDownTimer(Util.TIME_COUNTDOWN, 1000) {

            override fun onTick(millisRemaining: Long) {
                handleTimerValues(millisRemaining.formatTime(), false)
                _complete.postValue(false)
            }

            override fun onFinish() {
                _complete.postValue(true)
            }
        }.start()
    }

    private fun handleTimerValues(
        text: String,
        celebrate: Boolean
    ) {
        _complete.postValue(celebrate)
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}