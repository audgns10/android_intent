package com.school_of_company.intent_sample_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.intent_sample_project.ui.theme.Intent_sample_projectTheme

class ShareActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        var sharedText = "No Text Shared"
        if (intent?.action == Intent.ACTION_SEND) {
            if ("text/plain" == intent.type) {
                sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: "텍스트 없음"
            }
        }

        setContent {
            Intent_sample_projectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShareContent(
                        text = sharedText,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ShareContent(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Share Activity",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "공유 텍스트: $text",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
