package bd.edu.ist

import android.graphics.Color
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import bd.edu.ist.db.CourseEntity

@Composable
fun CourseListComponent(courses: List<CourseEntity>, onFavoriteClick: (course: CourseEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(courses) { course ->
            Surface(color = colorResource(id = R.color.ist)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Column(modifier = Modifier.padding(5.dp)) {
                        Button(onClick = {onFavoriteClick(course)}) {
                            val favoriteText = if (course.favorite==false || course.favorite==null) "Add to favorite" else "Remove from favorite"
                            Text(text = favoriteText)
                        }
                        if (course.major != null && course.major != "") {
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Major: ")
                                    }
                                    append(course.major!!)
                                }
                            )
                        }
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Title: ")
                                }
                                append(course.title ?: "")
                            }
                        )
                        Text(
                            buildAnnotatedString {
                                if (course.code != "0") {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Code: ")
                                    }
                                    append(course.code ?: "")
                                    append("   ")
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Credit: ")
                                }
                                append(course.credit ?: "")
                                if (course.hour != "0") {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("   Hour: ")
                                    }
                                    append(course.hour ?: "")
                                }
                            }
                        )
                        if (!course.content.isNullOrBlank()) {
                            Text(text = "Content: ", fontWeight = FontWeight.Bold)
                            AndroidView(factory = {
                                TextView(it).apply {
                                    text = course.content
                                    setTextColor(Color.parseColor("#000000"))
                                }
                            })
                        }
                        if (!course.book.isNullOrBlank()) {
                            Text(text = "Books: ", fontWeight = FontWeight.Bold)
                            AndroidView(factory = {
                                TextView(it).apply {
                                    text = course.book
                                    setTextColor(Color.parseColor("#000000"))
                                }
                            })
                        }

                    }
                }
            }
        }
    }
}