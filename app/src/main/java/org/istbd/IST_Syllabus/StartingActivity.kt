package org.istbd.IST_Syllabus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class StartingActivity : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartActivityContent() }
        goToNextPage()
    }

    private fun goToNextPage() {
        Handler().postDelayed({
            val menuIntent = Intent(this@StartingActivity, MainActivity::class.java)
            startActivity(menuIntent)
            finish()
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }, 500)
    }
}

@Preview("Starting Activity Preview", showBackground = true)
@Composable
fun StartingActivityPreview() {
    StartActivityContent()
}