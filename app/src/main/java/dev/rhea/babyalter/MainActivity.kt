package dev.rhea.babyalter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.preferencesDataStore
import dev.rhea.babyalter.data.BIRTH_DATE_KEY
import dev.rhea.babyalter.data.BIRTH_DATE_CALC_KEY
import dev.rhea.babyalter.ui.screens.BabyalterScreen
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.rhea.babyalter.ui.theme.BabyalterTheme

val ComponentActivity.dataStore by preferencesDataStore(name = "baby_prefs")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val birthDate by dataStore.data
                .map { it[BIRTH_DATE_KEY]?.let(LocalDate::parse) }
                .collectAsState(initial = null )

            val birthDateCalc by dataStore.data
                .map { it[BIRTH_DATE_CALC_KEY]?.let(LocalDate::parse) }
                .collectAsState(initial = null)

            BabyalterTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { padding ->
                        BabyalterScreen(
                            birthDate = birthDate,
                            birthDateCalc = birthDateCalc,
                            activity = this,
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}
