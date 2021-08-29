package com.emrizkiem.backgroundprocess

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emrizkiem.backgroundprocess.alarmmanager.AlarmManagerActivity
import com.emrizkiem.backgroundprocess.backgroundthread.BackgroundThreadActivity
import com.emrizkiem.backgroundprocess.broadcastreceiver.MyBroadcastReceiver
import com.emrizkiem.backgroundprocess.databinding.ActivityMainBinding
import com.emrizkiem.backgroundprocess.service.ServiceActivity

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnBgThread?.setOnClickListener {
            startActivity(Intent(this, BackgroundThreadActivity::class.java))
        }

        binding?.btnService?.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }

        binding?.btnSmsReceiver?.setOnClickListener {
            startActivity(Intent(this, MyBroadcastReceiver::class.java))
        }

        binding?.btnAlarmManager?.setOnClickListener {
            startActivity(Intent(this, AlarmManagerActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}