package dev.rhea.babyalter.ui.components

import android.app.DatePickerDialog
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.rhea.babyalter.data.calculateAge
import dev.rhea.babyalter.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BirthDateSection(
    title: String,
    date: LocalDate?,
    prefKey: Preferences.Key<String>,
    activity: ComponentActivity
) {
    val scope = rememberCoroutineScope()
    var today by remember { mutableStateOf(LocalDate.now()) }
    var pickedDate by remember { mutableStateOf<LocalDate?>(null) }

    // Mitternacht-Update
    LaunchedEffect(Unit) {
        while (true) {
            val now = LocalDate.now()
            if (now != today) today = now
            delay(60_000)
        }
    }

    // Speichern nach Auswahl
    LaunchedEffect(pickedDate) {
        pickedDate?.let { date ->
            scope.launch {
                activity.dataStore.edit { prefs ->
                    prefs[prefKey] = date.toString()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(title, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(12.dp))

        if (date == null) {
            Button(onClick = {
                showDatePicker(activity, today) { pickedDate = it }
            }) {
                Text("Datum auswählen", fontSize = 18.sp)
            }
        } else {
            val age = calculateAge(date, today)

            Text("Tage: ${age.daysTotal}", fontSize = 18.sp)
            Text(
                "Wochen: ${age.weeks} + ${age.remainingDaysAfterWeeks} Tage",
                fontSize = 18.sp
            )
            Text(
                "Monate: ${age.months} + ${age.remainingWeeksAfterMonths} Wochen + ${age.remainingDaysAfterMonths} Tage",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                OutlinedButton(onClick = {
                    showDatePicker(activity, today) { pickedDate = it }
                }) {
                    Text("Ändern")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(onClick = {
                    scope.launch {
                        activity.dataStore.edit { prefs ->
                            prefs.remove(prefKey)
                        }
                    }
                }) {
                    Text("Löschen")
                }
            }
        }
    }
}

fun showDatePicker(
    activity: ComponentActivity,
    today: LocalDate,
    onPicked: (LocalDate) -> Unit
) {
    DatePickerDialog(
        activity,
        { _, y, m, d ->
            onPicked(LocalDate.of(y, m + 1, d))
        },
        today.year,
        today.monthValue - 1,
        today.dayOfMonth
    ).show()
}
