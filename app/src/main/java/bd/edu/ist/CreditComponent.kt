package bd.edu.ist

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import bd.edu.ist.ui.theme.ISTTheme

class Credit : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreditComponent()
        }
    }
}

@Composable
@Preview("Credit Component")
fun CreditComponent() {
    ISTTheme {
        Surface(color = Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(state = ScrollState(0)),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.developer),
                    contentDescription = stringResource(id = R.string.developer),
                    modifier = Modifier
                        .height(160.dp)
                        .width(190.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                AndroidView(
                    factory = {
                        TextView(it).apply {
                            text = it.getText(R.string.developer)
                            setTextAppearance(it, android.R.style.TextAppearance_Medium)
                        }
                    }, modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(bottom = 10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Image(
                    painter = painterResource(id = R.drawable.shovon),
                    contentDescription = stringResource(id = R.string.designer),
                    modifier = Modifier
                        .height(160.dp)
                        .width(190.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                AndroidView(
                    factory = {
                        TextView(it).apply {
                            text = it.getText(R.string.designer)
                            setTextAppearance(it, android.R.style.TextAppearance_Medium)
                        }
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(alignment = Alignment.CenterHorizontally),
                )
            }

        }
    }
}