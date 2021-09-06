package org.istbd.IST_Syllabus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.istbd.IST_Syllabus.ui.theme.ISTTheme

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