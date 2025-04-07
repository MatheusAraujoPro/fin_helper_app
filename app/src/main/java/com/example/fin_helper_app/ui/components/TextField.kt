package com.example.fin_helper_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fin_helper_app.ui.theme.componentBackground

@Composable
fun CustomTextField(
    inputText: String,
    placeholder: String? = null,
    isMaxLength: Boolean = true,
    isEnable: Boolean = true,
    textFieldType: TextFieldType = TextFieldType.DEFAULT,
    onChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        TextField(
            value = inputText,
            enabled = isEnable,
            placeholder = {
                placeholder?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp
                    )
                }
            },
            onValueChange = {
                onChange.invoke(it)
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = componentBackground,
                focusedContainerColor = componentBackground,
                disabledContainerColor = componentBackground,
                disabledTextColor = Color.White,
                focusedPlaceholderColor = Color.White,
                unfocusedPlaceholderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.then(
                if (isMaxLength) Modifier.fillMaxWidth() else Modifier.fillMaxWidth(0.82f)
            ),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            keyboardOptions =
            if (textFieldType == TextFieldType.NUMBER)
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            else
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
        )
    }
}

enum class TextFieldType(val type: Int) {
    DEFAULT(type = 0),
    NUMBER(type = 1);

    companion object {
        fun valueOf(type: Int) = entries.firstOrNull { it.type == type }
    }
}