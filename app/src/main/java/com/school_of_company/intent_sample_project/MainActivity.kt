package com.school_of_company.intent_sample_project

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.intent_sample_project.ui.theme.Intent_sample_projectTheme
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Intent_sample_projectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntentDashboard(
                        modifier = Modifier.padding(innerPadding),
                        onExplicit = { startExplicit() },
                        onImplicitUrl = { startImplicitUrl() },
                        onImplicitShare = { startImplicitShare() },
                        onBroadcast = { sendCustomBroadcast() },
                        onPendingIntent = { triggerPendingIntent() },
                        onSafeCopy = { testSafeIntentCopy() }
                    )
                }
            }
        }
    }

    // 1. 명시적 인텐트 (기존 구현 강화)
    private fun startExplicit() {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_TITLE", "명시적 인텐트 테스트")
            putExtra("EXTRA_ID", 1011)
        }
        startActivity(intent)
    }

    // 2. 암시적 인텐트 (URL 열기)
    private fun startImplicitUrl() {
        val intent = Intent(Intent.ACTION_VIEW, "https://www.lemonhealthcare.com/".toUri())
        startActivity(intent)
    }

    // 3. 암시적 인텐트(텍스트 공유)
    private fun startImplicitShare() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "뀨뀨뀨")
            type = "text/plain"
        }
        // createChooser() 사용으로 항상 앱 선택기 표시
        val shareIntent = Intent.createChooser(sendIntent, "뀨뀨")
        startActivity(shareIntent)
    }

    // 4. 브로드캐스트 전송 (3가지 기본 사례 중 하나)
    private fun sendCustomBroadcast() {
        val intent = Intent("com.school_of_company.CUSTOM_ACTION").apply {
            putExtra("EXTRA_MESSAGE", "브로드캐스트 메세지")
            // Android 8.0+ 암시적 브로드캐스트 제한 우회 (자사 앱 타겟)
            setPackage(packageName)
        }
        sendBroadcast(intent)
    }

    // 5. PendingIntent 실행 (보안 권장사항)
    private fun triggerPendingIntent() {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_TITLE", "PendingIntent 테스트")
        }
        // FLAG_IMMUTABLE 또는 FLAG_MUTABLE 명시 필수 (Android 12+)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        try {
            pendingIntent.send()
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }
    }

    // 6. 안전한 Intent 데이터 복사
    private fun testSafeIntentCopy() {
        // 원본 인텐트 (여러 정보가 섞여있음)
        val originalIntent = Intent().apply {
            putExtra("title", "타이틀")
            putExtra("dangerous_key", "보안키")
        }
        
        // 필요한 정보만 골라서 복사 (리드미 예시 코드 적용)
        val safeIntent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_TITLE", originalIntent.getStringExtra("title"))
            putExtra("EXTRA_ID", 1011)
        }
        startActivity(safeIntent)
    }
}

@Composable
private fun IntentDashboard(
    modifier: Modifier = Modifier,
    onExplicit: () -> Unit,
    onImplicitUrl: () -> Unit,
    onImplicitShare: () -> Unit,
    onBroadcast: () -> Unit,
    onPendingIntent: () -> Unit,
    onSafeCopy: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Android Intent Sample Project_이명훈",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        DashboardButton("1. 명시적 (내부 화면 이동)", onExplicit)
        DashboardButton("2. 암시적 (웹 URL 열기)", onImplicitUrl)
        DashboardButton("3. 암시적 (Chooser로 공유)", onImplicitShare)
        DashboardButton("4. 브로드캐스트 전송 (토스트)", onBroadcast)
        DashboardButton("5. PendingIntent 실행", onPendingIntent)
        DashboardButton("6. 필요한 데이터만 안전 복사", onSafeCopy)
    }
}

@Composable
private fun DashboardButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}
