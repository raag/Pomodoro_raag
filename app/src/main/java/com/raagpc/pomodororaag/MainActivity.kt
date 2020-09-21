package com.raagpc.pomodororaag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.raagpc.pomodororaag.databinding.ActivityMainBinding
import com.raagpc.timerview.TimerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var timer: TimerView
    private lateinit var controlButton: ImageButton
    private lateinit var resetButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this

        val provider = MainActivityViewModelFactory(context = this)
        viewModel = ViewModelProvider(this, provider).get(MainActivityViewModel::class.java)

        viewModel.status.value?.let { Log.i("TAG", it.toString()) }

        mBinding.viewModel = viewModel

        setupUI()
        setupEvents()
        setupObservers()
    }

    private fun setupUI() {
        timer = findViewById(R.id.timer)
        controlButton = findViewById(R.id.control_button)
        resetButton = findViewById(R.id.reset_button)

        timer.setTimerViewListener(timerListener)
    }

    private fun setupEvents() {
        controlButton.setOnClickListener {
            viewModel.toggleTimer()
        }

        resetButton.setOnClickListener {
            viewModel.resetTimer()
        }
    }

    private fun setupObservers() {
        viewModel.status.observeForever {
            updateStatus(it)
        }
    }

    private fun updateStatus(status: MainActivityViewModel.UIStatus) {
        when (status) {
            MainActivityViewModel.UIStatus.READY_TO_WORK -> getReadyToWork()
            MainActivityViewModel.UIStatus.READY_TO_BREAK -> getReadyToBreak()
            MainActivityViewModel.UIStatus.RUNNING_WORK,
                MainActivityViewModel.UIStatus.RUNNING_BREAK -> onStartTimer()
            MainActivityViewModel.UIStatus.PAUSE_WORK,
                MainActivityViewModel.UIStatus.PAUSE_BREAK -> onPauseTimer()
            MainActivityViewModel.UIStatus.LOADING -> {}
        }
    }

    private fun getReadyToWork() {
        val timeToCount = viewModel.getTimeToCount()
        timer.stop()
        timer.setMaxValue(timeToCount)
        timer.setValue(timeToCount)
    }

    private fun getReadyToBreak() {
        val timeToCount = viewModel.getTimeToCount()
        timer.stop()
        timer.setMaxValue(timeToCount)
        timer.setValue(timeToCount)
    }

    private fun onStartTimer() {
        timer.start()
    }

    private fun onPauseTimer() {
        timer.pause()
    }

    private val timerListener = object: TimerView.TimerViewListener {
        override fun onFinish(timerView: TimerView) {
            viewModel.onTimerFinish()
        }

        override fun onTick(time: Int, timerView: TimerView) {

        }

    }

}