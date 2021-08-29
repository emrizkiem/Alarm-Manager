package com.emrizkiem.backgroundprocess.broadcastreceiver

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.emrizkiem.backgroundprocess.PermissionManager
import com.emrizkiem.backgroundprocess.R
import com.emrizkiem.backgroundprocess.databinding.ActivityMyBroadcastReceiverBinding

class MyBroadcastReceiver : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val SMS_REQUEST_CODE = 101
    }

    private var binding: ActivityMyBroadcastReceiverBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission -> PermissionManager.check(
                this,
                android.Manifest.permission.RECEIVE_SMS,
                SMS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}