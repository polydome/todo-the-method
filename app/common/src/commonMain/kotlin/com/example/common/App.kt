package com.example.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

suspend fun getMessage(): String {
    HttpClient().use { client ->
        val response = client.get("https://d288-83-6-145-101.eu.ngrok.io/tasks")
        return response.bodyAsText()
    }
}

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var message by remember { mutableStateOf("Waiting") }
    val platformName = getPlatformName()

    CoroutineScope(Dispatchers.Unconfined).launch {
        message = getMessage()
    }

    Column(modifier = Modifier.verticalScroll(enabled = true, state = rememberScrollState())) {
        Text(message)
        Button(onClick = {
            text = "Hello, ${platformName}"
        }) {
            Text(text)
        }
    }
}
