package com.emrizkiem.backgroundprocess.alarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emrizkiem.backgroundprocess.R
import com.emrizkiem.backgroundprocess.databinding.ActivityAlarmManagerBinding
import com.emrizkiem.backgroundprocess.utils.DatePickerFragment
import com.emrizkiem.backgroundprocess.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AlarmManagerActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private var binding: ActivityAlarmManagerBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlarmManagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        alarmReceiver = AlarmReceiver()

        binding?.btnOnceDate?.setOnClickListener {
            val dateTimePicker = DatePickerFragment()
            dateTimePicker.show(supportFragmentManager, DATE_PICKER_TAG)
        }

        binding?.btnOnceTime?.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
        }

        binding?.btnSetOnceAlarm?.setOnClickListener {
            val onceDate = binding?.tvOnceDate?.text.toString()
            val onceTime = binding?.tvOnceTime?.text.toString()
            val onceMessage = binding?.etOnceMsg?.text.toString()
            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                onceDate,
                onceTime,
                onceMessage)
        }
    }

    override fun onDialogDataSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Siapkan date formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        // Set text dari textview once
        binding?.tvOnceDate?.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Set text dari textview berdasarkan tag
        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> {}
            else -> {
            }
        }
    }
}