package dev.rhea.babyalter.ui.components

import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.core.net.toUri

@Composable
fun Footer(
    linkText: String,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Text(
        text = linkText,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline,
        modifier = modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    )
}