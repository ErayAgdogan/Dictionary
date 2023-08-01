package com.goander.dictionary.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import java.util.regex.Pattern

@Composable
public fun EmphasizedText(
    modifier: Modifier = Modifier,
    text: String,
    emphasizedPartOfText: String,
    ignoreCaseOnHighLight: Boolean = true,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = buildAnnotatedString {

            //https://stackoverflow.com/a/2206432
            val textList =  Pattern.compile(
                "((?<=$emphasizedPartOfText)|(?=$emphasizedPartOfText))",
                if (ignoreCaseOnHighLight) Pattern.CASE_INSENSITIVE else 0
            ).split(text)


            for (text in  textList) {
                if (text.equals(emphasizedPartOfText, ignoreCase = true))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text)
                    }
                else {
                    append(text)
                }
            }

        },
    )
}