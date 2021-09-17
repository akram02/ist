package bd.edu.ist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import bd.edu.ist.ui.theme.ISTTheme

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
fun StartActivityContent() {
    ISTTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Image(
                    painter = painterResource(id = R.drawable.ist_logo_vertical),
                    contentDescription = "IST",
                    modifier = Modifier.fillMaxHeight().fillMaxWidth()
                )
            }
        }
    }
}