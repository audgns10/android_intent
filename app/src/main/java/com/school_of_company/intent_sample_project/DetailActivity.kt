package com.school_of_company.intent_sample_project

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

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Receive data from Intent
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "타이틀 없음"
        val id = intent.getIntExtra("EXTRA_ID", -1)

        setContent {
            Intent_sample_projectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailContent(
                        title = title,
                        id = id,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    id: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Detail Activity",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "타이틀: $title",
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "아이디: $id",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
