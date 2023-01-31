package com.goander.dictionary.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goander.dictionary.R
import com.goander.dictionary.ui.theme.Silver
import com.goander.dictionary.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun StartScreen(
    startViewModel: StartViewModel = viewModel(),
    navigateToSearch: () -> Unit = {},
    navigateToSettings: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        topBar = {
            Box(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .size(72.dp),
                    painter = painterResource(id = R.drawable.ic_logo_24),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 68.dp),
                    text = stringResource(id = R.string.ictionary),
                    fontSize = 24.sp,
                )

                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { navigateToSettings() },
                    painter = painterResource(id = R.drawable.ic_round_settings_24),
                    contentDescription = "",
                    tint = Silver,
                )
            }

        }
    ) {
          ConstraintLayout(modifier = Modifier
              .padding(8.dp)
              .fillMaxSize()
              .padding(it)) {
              val (textStart, textSearching, cardSearch) = createRefs()
              Card(
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(56.dp)
                      .constrainAs(cardSearch) {
                          start.linkTo(parent.start)
                          top.linkTo(parent.top)
                          bottom.linkTo(parent.bottom)
                          end.linkTo(parent.end)
                      },
                  onClick = { navigateToSearch() },
              ) {
                  Image(
                      modifier = Modifier
                          .fillMaxHeight()
                          .padding(end = 4.dp)
                          .width(36.dp)
                          .align(Alignment.End),
                      painter = painterResource(id = R.drawable.ic_round_search_24),
                      contentDescription = stringResource(id = R.string.search),
                      colorFilter = ColorFilter.tint(color = Silver)
                  )

              }
              Text(
                  modifier = Modifier
                      .padding(bottom = 8.dp)
                      .constrainAs(textSearching) {
                          start.linkTo(parent.start)
                          bottom.linkTo(cardSearch.top)
                      },
                  text = stringResource(id = R.string.searching),
                  fontWeight = FontWeight.Bold,
                  fontSize = 40.sp
              )

              Text(
                  modifier = Modifier
                      .padding(bottom = 16.dp)
                      .constrainAs(textStart) {
                          start.linkTo(parent.start)
                          bottom.linkTo(textSearching.top)

                      },
                  text = stringResource(id = R.string.Start),
                  fontWeight = FontWeight.Bold,
                  fontSize = 40.sp,
              )


          }
    }
}


@Preview
@Composable
private fun StartPagePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey40)
    ) {
        StartScreen(startViewModel = StartViewModel(SavedStateHandle()))
    }
}