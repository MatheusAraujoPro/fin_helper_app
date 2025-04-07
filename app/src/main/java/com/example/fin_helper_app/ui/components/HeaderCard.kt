package com.example.fin_helper_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fin_helper_app.R
import com.example.fin_helper_app.ui.model.HeaderCardType
import com.example.fin_helper_app.helper.asCurrency

@Composable
fun HeaderCard(
    value: Double,
    headerCardType: HeaderCardType,
    screenPercentWidth: Float
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(
            16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF323238)
        ),
        modifier = Modifier
            .width(screenPercentWidth.dp)
            .height(150.dp)
    ) {
        CardContent(
            value = value,
            screenPercentWidth = screenPercentWidth,
            headerCardType = headerCardType
        )
    }
}

@Composable
private fun CardContent(
    value: Double,
    screenPercentWidth: Float,
    headerCardType: HeaderCardType,
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .width(screenPercentWidth.dp)

    ) {
        Row(
            modifier = Modifier
                .width(screenPercentWidth.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = decideText(headerCardType = headerCardType),
                color = Color.White.copy(
                    alpha = 0.7f
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(decideIcon(headerCardType = headerCardType)),
                contentDescription = null,
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = value.asCurrency(),
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

@Composable
internal fun decideIcon(headerCardType: HeaderCardType) =
    when (headerCardType) {
        HeaderCardType.BALANCE -> R.drawable.payment
        HeaderCardType.GENIAL_CARD, HeaderCardType.NUBANK_CARD -> R.drawable.credit_card
        HeaderCardType.INVESTMENTS -> R.drawable.account_balance
    }

@Composable
internal fun decideText(headerCardType: HeaderCardType) =
    when (headerCardType) {
        HeaderCardType.BALANCE -> stringResource(R.string.free_balance)
        HeaderCardType.GENIAL_CARD -> stringResource(R.string.genial_balance)
        HeaderCardType.NUBANK_CARD -> stringResource(R.string.nubank_balance)
        HeaderCardType.INVESTMENTS -> stringResource(R.string.account_balance)
    }


@Preview
@Composable
fun HeaderCardPreview() {
    Column(Modifier.fillMaxSize()) {
        HeaderCard(value = 0.0, headerCardType = HeaderCardType.BALANCE, 270.0f)
        Spacer(modifier = Modifier.height(8.dp))
        HeaderCard(0.0, headerCardType = HeaderCardType.GENIAL_CARD, 270.0f)
        Spacer(modifier = Modifier.height(8.dp))
        HeaderCard(0.0, headerCardType = HeaderCardType.NUBANK_CARD, 270.0f)
        Spacer(modifier = Modifier.height(8.dp))
        HeaderCard(0.0, headerCardType = HeaderCardType.INVESTMENTS, 270.0f)
        Spacer(modifier = Modifier.height(8.dp))
    }
}