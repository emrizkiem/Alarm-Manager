package com.emrizkiem.backgroundprocess.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.emrizkiem.backgroundprocess.R

class ServiceActivity : AppCompatActivity() {

    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val btnStartService = findViewById<Button>(R.id.btn_start_service)
        btnStartService.setOnClickListener {
            val startServiceIntent = Intent(this, MyService::class.java)
            startService(startServiceIntent)
        }

        val btnStartJobIntentService = findViewById<Button>(R.id.btn_start_service_with_job_intent)
        btnStartJobIntentService.setOnClickListener {
            val startJobIntentService = Intent(this, MyIntentService::class.java)
            startJobIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L)
            MyIntentService.enqueueWork(this, startJobIntentService)
        }

        val btnStartBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        btnStartBoundService.setOnClickListener {
            val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }

        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {
            unbindService(mServiceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection) // Menghindari memory leaks
        }
    }
}