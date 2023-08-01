package com.goander.dictionary.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goander.dictionary.theme.themeColors

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
public fun SelectColorDialog(
    selectedTheme: Int,
    onThemeSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {

    var preSelectedTheme by rememberSaveable {
        mutableStateOf(selectedTheme)
    }

    SettingAlertDialog(
        title = stringResource(
            id = R.string.theme),
        onDismissRequest = onDismissRequest,
        onConfirm = { onThemeSelected(preSelectedTheme) }
    ) {
        FlowRow(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {

            for (themeColor in themeColors)
                if (themeColor.seed.toArgb() != preSelectedTheme)
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(36.dp),
                        colors = CardDefaults.cardColors(containerColor = themeColor.seed),
                        shape = RoundedCornerShape(4.dp),
                        onClick = { preSelectedTheme = themeColor.seed.toArgb() }
                    ) {

                    }
                else
                    OutlinedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(36.dp)
                            .align(Alignment.CenterVertically),
                        border = BorderStroke(width = 2.dp, color = themeColor.seed),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Card(
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.Center),
                                colors = CardDefaults.cardColors(containerColor = themeColor.seed),
                                shape = RoundedCornerShape(2.dp)
                            ) {

                            }
                        }

                    }


        }
    }

}
