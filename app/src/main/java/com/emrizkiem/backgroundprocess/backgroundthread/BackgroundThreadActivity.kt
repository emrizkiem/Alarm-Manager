package com.emrizkiem.backgroundprocess.backgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.emrizkiem.backgroundprocess.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class BackgroundThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_thread)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

//        btnStart.setOnClickListener {
//            try {
//                // Simulate for process compressing
//                for (i in 0..10) {
//                    Thread.sleep(500)
//                    val percentage = i * 10
//                    if (percentage == 100) {
//                        tvStatus.setText(R.string.task_completed)
//                    } else {
//                        tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                    }
//                }
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        }

//        btnStart.setOnClickListener {
//            executor.execute {
//                try {
//                    // Simulate process in Background Thread
//                    for (i in 0..10) {
//                        Thread.sleep(500)
//                        val percentage = i * 10
//                        handler.post {
//                            // Update UI in Main Thread
//                            if (percentage == 100) {
//                                tvStatus.setText(R.string.task_completed)
//                            } else {
//                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                            }
//                        }
//                    }
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }

        /*
            Coroutines
            Dispatchers.Default = Background Process
            Dispatchers.Main = Main / UI process
         */
        btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                for (i in 0..10) {
                    delay(500)
                    val percentage = i * 10
                    withContext(Dispatchers.Main) {
                        if (percentage == 100) {
                            tvStatus.setText(R.string.task_completed)
                        } else {
                            tvStatus.text = String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}