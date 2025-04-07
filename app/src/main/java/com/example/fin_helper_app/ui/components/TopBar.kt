package com.example.fin_helper_app.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fin_helper_app.R
import com.example.fin_helper_app.ui.model.HeaderCardModel
import com.example.fin_helper_app.ui.model.HeaderCardType

@Composable
fun TopBar(
    headerTypeMap: Map<HeaderCardType, Double>,
    transactionsListSize: Int
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val screenHeight = config.screenHeightDp
    val screenPercentWidth = (screenWidth * 0.7).toFloat()
    val screenPercentHeight = (screenHeight * 0.35).toFloat()
    val headerList = listOf(
        HeaderCardModel(
            type = HeaderCardType.BALANCE,
            value = 0.0
        ),
        HeaderCardModel(
            type = HeaderCardType.GENIAL_CARD,
            value = 0.0
        ),
        HeaderCardModel(
            type = HeaderCardType.NUBANK_CARD,
            value = 0.0
        ),
        HeaderCardModel(
            type = HeaderCardType.INVESTMENTS,
            value = 0.0
        )
    )

    Log.d("teste", "TopBar: ")

    Content(
        headerModelList = headerList,
        headerTypeMap = headerTypeMap,
        transactionsListSize = transactionsListSize,
        screenPercentHeight = screenPercentWidth,
        screenPercentWidth = screenPercentHeight
    )
}

@Composable
private fun Content(
    headerModelList: List<HeaderCardModel>,
    headerTypeMap: Map<HeaderCardType, Double>,
    transactionsListSize: Int,
    screenPercentWidth: Float,
    screenPercentHeight: Float
) {
    val realHeight = screenPercentHeight.dp + 30.dp
    Box(
        modifier = Modifier
            .height(realHeight)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Black)
                .height(186.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .align(Alignment.Center)
            )
        }
        LazyRow(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = 64.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(headerModelList) { item ->
                val value = headerTypeMap[item.type]
                HeaderCard(
                    value = value ?: 0.0,
                    headerCardType = item.type,
                    screenPercentWidth = screenPercentWidth
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.top_bar_transactions),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Text(
                text = if (transactionsListSize == 0 || transactionsListSize > 1) stringResource(
                    R.string.top_bar_items_plural,
                    transactionsListSize.toString()
                ) else
                    stringResource(
                        R.string.top_bar_items_singular,
                        transactionsListSize.toString()
                    ),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
private fun TopBarPreview() {
    Column(Modifier.fillMaxSize()) {
        TopBar(
            headerTypeMap = mapOf(
                Pair(
                    HeaderCardType.BALANCE,
                    50.0
                ),
                Pair(
                    HeaderCardType.NUBANK_CARD,
                    50.0
                ),
                Pair(
                    HeaderCardType.GENIAL_CARD,
                    50.0
                )
            ),
            transactionsListSize = 10
        )
    }
}