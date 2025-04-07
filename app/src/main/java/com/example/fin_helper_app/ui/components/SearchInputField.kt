package com.example.fin_helper_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fin_helper_app.ui.theme.componentBackground

@Composable
fun SearchInputField(
    inputText: String,
    modifier: Modifier,
    onChange: (String) -> Unit,
    onClickButton: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        CustomTextField(
            inputText = inputText,
            onChange = onChange,
            isMaxLength = false
        )
        IconButton {
            onClickButton.invoke()
        }
    }
}

@Composable
private fun IconButton(
    onClickButton: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF00875F),
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = componentBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(12.dp)
            .clickable {
                onClickButton.invoke()
            }
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color(0xFF00875F),
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
private fun SearchInputFieldPreview() {
    Column(Modifier.fillMaxSize()) {
        SearchInputField(
            inputText = "Espetinho do nonato",
            modifier = Modifier,
            onChange = {},
            onClickButton = {}
        )
    }
}