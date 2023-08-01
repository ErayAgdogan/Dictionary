package com.goander.dictionary.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.goander.dictionary.design.DictionaryIcon
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
public fun ConnectionInformerBanner(isConnectionAvailable: Boolean) {
    var shouldBannerVisible by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit, block = {
        delay(200.milliseconds)
        shouldBannerVisible = true
    })
    if (shouldBannerVisible)
        AnimatedVisibility(visible = !isConnectionAvailable) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                Icon(
                    painter = painterResource(id = DictionaryIcon.NoInternet),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "connection unavailable",
                )
            }
        }



}