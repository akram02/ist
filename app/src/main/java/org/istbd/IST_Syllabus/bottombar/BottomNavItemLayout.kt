package org.istbd.IST_Syllabus

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import com.google.android.material.math.MathUtils

@Composable
fun BottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        content = {
            IconBox(icon)
            val scale = MathUtils.lerp(0.6f, 1f, animationProgress)
            TextBox(animationProgress, scale, text)
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        placeTextAndIcon(
            textPlaceable,
            iconPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress
        )
    }
}

@Composable
private fun TextBox(
    animationProgress: Float,
    scale: Float,
    text: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        modifier = Modifier
            .layoutId("text")
            .padding(horizontal = TextIconSpacing)
            .graphicsLayer {
                alpha = animationProgress
                scaleX = scale
                scaleY = scale
                transformOrigin = BottomNavLabelTransformOrigin
            },
        content = text
    )
}

@Composable
private fun IconBox(icon: @Composable() (BoxScope.() -> Unit)) {
    Box(
        modifier = Modifier
            .layoutId("icon")
            .padding(horizontal = TextIconSpacing),
        content = icon
    )
}

fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.placeRelative(iconX.toInt(), iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(textX.toInt(), textY)
        }
    }
}
