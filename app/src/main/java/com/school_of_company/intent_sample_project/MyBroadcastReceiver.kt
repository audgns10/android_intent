package com.school_of_company.intent_sample_project

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "메세지 없음"
        Toast.makeText(context, "브로드캐스트 수신: $message", Toast.LENGTH_SHORT).show()
    }
}
