package com.goander.dictionary.ui

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
public fun launcherSpeechToText(onTextReceived: (String) -> Unit): () -> Unit {
    // Thanks to https://stackoverflow.com/a/70424733/15038120
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toLanguageTag())
    val context = LocalContext.current

    val pendIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode != Activity.RESULT_OK) {
            return@rememberLauncherForActivityResult
        }

        //GET TEXT ARRAY FROM VOICE INTENT
        val result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

        if (result !== null) {
            onTextReceived(result[0] ?: "")
        }
    }
    return {
        launcher.launch(
            IntentSenderRequest
                .Builder(pendIntent)
                .build()
        )
    }
}
