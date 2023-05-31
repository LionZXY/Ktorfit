package de.jensklingenberg.androidonlyexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import de.jensklingenberg.androidonlyexample.ui.theme.AndroidOnlyExampleTheme
import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json { isLenient = true; ignoreUnknownKeys = true }

val ktorfit = ktorfit {
    baseUrl("https://catalog.flipp.dev/api/v0/")
    httpClient(HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    })
    converterFactories(
        FlowConverterFactory(),
        CallConverterFactory(),
    )

}
val api: StarWarsApi = ktorfit.create<StarWarsApi>()

class MainActivity : ComponentActivity() {


    private val peopleState = mutableStateOf<KtorfitApplicationShort?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidOnlyExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    peopleState.value?.let {
                        Text(it.name ?: "")
                    }

                }
            }
        }

        lifecycleScope.launch {
            // Loaded successful:
            val response = api.getApplicationsString()
            Log.i("MainActivity", response)
            // Decoded successful:
            val decodedAnswer = json.decodeFromString<Array<KtorfitApplicationShort>>(response)
            Log.i("MainActivity", decodedAnswer.toList().toString())
            // Crash:
            peopleState.value = api.getApplications().firstOrNull()
        }
    }
}