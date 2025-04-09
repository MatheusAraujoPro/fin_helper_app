package com.example.fin_helper_app.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fin_helper_app.R
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.helper.asCurrency
import com.example.fin_helper_app.ui.theme.ignitedBrandRed
import com.example.fin_helper_app.ui.theme.ignitedMidGreen
import kotlinx.coroutines.delay
import kotlin.math.log

@Composable
fun TransactionCard(
    transactionModel: TransactionModel,
    onCardTap: () -> Unit,
    onDelete: () -> Unit
) {
    SwipeToDeleteContainer(
        item = transactionModel,
        onDelete = { onDelete.invoke() },
        content = {
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
                    .fillMaxWidth()
                    .clickable {
                        onCardTap.invoke()
                    }

            ) {
                CardContent(
                    title = transactionModel.name,
                    incomeType = transactionModel.incomeType,
                    date = transactionModel.createdAt,
                    value = transactionModel.value,
                    transactionType = transactionModel.type
                )
            }
        }
    )

}

@Composable
private fun CardContent(
    title: String,
    incomeType: IncomeType,
    date: String,
    value: Double,
    transactionType: TransactionType
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.White.copy(
                    alpha = 0.7f
                )
            )

            Icon(
                imageVector = decideIcon(transactionType = transactionType),
                contentDescription = null,
                tint = if (transactionType == TransactionType.EXPENSE) ignitedBrandRed else ignitedMidGreen,
                modifier = Modifier.size(24.dp)
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value.asCurrency(),
                color = Color.White,
                fontSize = 24.sp
            )

            Text(
                text = incomeType.description,
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = decideText(transactionCardType = transactionType),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )

            Text(
                text = date,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    val factorToSwipeTriggerTheDeleteAction = 1.5f
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        },
        positionalThreshold = {
            it.div(factorToSwipeTriggerTheDeleteAction)
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            enableDismissFromStartToEnd = false,
            content = { content(item) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        ignitedBrandRed
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color, shape = RoundedCornerShape(
                    16.dp
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun decideIcon(transactionType: TransactionType) =
    when (transactionType) {
        TransactionType.EXPENSE -> Icons.Filled.KeyboardArrowDown
        TransactionType.REVENUE -> Icons.Filled.KeyboardArrowUp
    }

@Composable
private fun decideText(transactionCardType: TransactionType) =
    when (transactionCardType) {
        TransactionType.EXPENSE -> stringResource(R.string.expense_text)
        TransactionType.REVENUE -> stringResource(R.string.revenue_text)
    }

enum class TransactionType(val value: Int) {
    EXPENSE(0),
    REVENUE(1);

    companion object {
        fun value(value: Int) =
            entries.firstOrNull { it.value == value }
    }
}

@Preview
@Composable
fun TransactionCardPreview() {
    Column(Modifier.fillMaxSize()) {
        TransactionCard(
            transactionModel = TransactionModel(
                name = "Espetinho do nonato",
                value = 0.0,
                type = TransactionType.EXPENSE,
                incomeType = IncomeType.NUBANK,
                createdAt = "12/03/2025"
            ),
            onCardTap = {},
            onDelete = {}

        )
        Spacer(modifier = Modifier.height(8.dp))
        TransactionCard(
            transactionModel = TransactionModel(
                name = "Espetinho do nonato",
                value = 0.0,
                type = TransactionType.EXPENSE,
                incomeType = IncomeType.GENIAL,
                createdAt = "12/03/2025"
            ),
            onCardTap = {},
            onDelete = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}