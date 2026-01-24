package dev.rhea.babyalter.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rhea.babyalter.data.BIRTH_DATE_KEY
import dev.rhea.babyalter.data.BIRTH_DATE_CALC_KEY
import dev.rhea.babyalter.ui.components.BirthDateSection
import dev.rhea.babyalter.ui.components.Footer
import java.time.LocalDate

@Composable
fun BabyalterScreen(
    birthDate: LocalDate?,
    birthDateCalc: LocalDate?,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            BirthDateSection(
                title = "Alter mit echtem Geburtstermin",
                date = birthDate,
                prefKey = BIRTH_DATE_KEY,
                activity = activity
            )

            Spacer(modifier = Modifier.height(32.dp))

            BirthDateSection(
                title = "Alter mit errechnetem Geburtstermin",
                date = birthDateCalc,
                prefKey = BIRTH_DATE_CALC_KEY,
                activity = activity
            )
        }
        Footer(
            linkText = "© 2026 Rhea · Open Source (MIT)",
            url = "https://github.com/gawrbot/babyalter",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
