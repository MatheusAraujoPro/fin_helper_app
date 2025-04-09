package com.example.fin_helper_app.presentation.summary.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fin_helper_app.R
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.ui.components.TransactionCard
import com.example.fin_helper_app.ui.components.TransactionType
import com.example.fin_helper_app.ui.theme.componentBackground
import com.example.fin_helper_app.ui.theme.customBackground
import com.example.fin_helper_app.ui.theme.ignitedMidGreen


fun decideSnackBarMessage(
    transactionType: TransactionType,
    context: Context,
    actionType: SnackBarActionType
): String {
    return when (actionType) {
        SnackBarActionType.ADD -> if (transactionType == TransactionType.REVENUE) context.getString(
            R.string.snackbar_revenue_message
        ) else context.getString(
            R.string.snackbar_expense_message
        )

        SnackBarActionType.DELETE -> if (transactionType == TransactionType.REVENUE) context.getString(
            R.string.snackbar_revenue_message_alter
        ) else context.getString(
            R.string.snackbar_expense_message_alter
        )

        SnackBarActionType.EDIT -> if (transactionType == TransactionType.REVENUE) context.getString(
            R.string.snackbar_revenue_message_edit
        ) else context.getString(
            R.string.snackbar_expense_message_edit
        )
    }
}

@Composable
fun BackgroundWrapper(
    content: @Composable () -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = componentBackground.copy(alpha = 0.9f)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        ) {
            when (isLoading) {
                true -> LoadingScreen()
                else -> content()
            }
        }
    }
}

@Composable
fun TransactionsCardList(
    transactions: List<TransactionModel>,
    filteredTransactions: List<TransactionModel>,
    onCardTap: (transaction: TransactionModel) -> Unit,
    onDelete: (transactionId: Long, transactionType: TransactionType) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        if (filteredTransactions.isNotEmpty())
            items(filteredTransactions) { transaction ->
                TransactionCard(
                    transactionModel = transaction,
                    onCardTap = {
                        onCardTap.invoke(transaction)

                    },
                    onDelete = {
                        transaction.id?.let { id ->
                            onDelete.invoke(id, transaction.type!!)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        else
            items(transactions) { transaction ->
                TransactionCard(
                    transactionModel = transaction,
                    onCardTap = {
                        onCardTap.invoke(transaction)
                    },
                    onDelete = {
                        transaction.id?.let { id ->
                            onDelete.invoke(id, transaction.type!!)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
    }
}

@Composable
fun FloatingOrbitalMenu(
    onChangeIncomeTypeValue: (IncomeType) -> Unit,
    expanded: Boolean,
    onChangeExpanded: (value: Boolean) -> Unit,
) = FloatButtonContent(
    onChangeIncomeTypeValue = onChangeIncomeTypeValue,
    onChangeExpanded = onChangeExpanded,
    expanded = expanded,
)

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = ignitedMidGreen,
            strokeWidth = 4.dp,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun FloatButtonContent(
    onChangeIncomeTypeValue: (IncomeType) -> Unit,
    expanded: Boolean,
    onChangeExpanded: (value: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.BottomEnd
    ) {
        val buttons = listOf(
            R.drawable.credit_card to stringResource(R.string.nubank_balance),
            R.drawable.credit_card to stringResource(R.string.genial_balance),
            R.drawable.account_balance to stringResource(R.string.free_balance)
        )

        buttons.forEachIndexed { index, (icon, description) ->
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + scaleIn() + slideInVertically { it * (index + 1) },
                exit = fadeOut() + scaleOut() + slideOutVertically { it * (index + 1) }
            ) {
                FloatingActionButton(
                    onClick = {
                        val incomeType = IncomeType.value(index)!!
                        onChangeIncomeTypeValue.invoke(incomeType)
                    },
                    modifier = Modifier
                        .padding(bottom = 80.dp + (index * 75).dp),
                    containerColor = decideColor(description)
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = description,
                        tint = Color.White
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { },
            shape = RoundedCornerShape(50.dp),
            contentColor = ignitedMidGreen,
            containerColor = componentBackground,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ignitedMidGreen,
                    shape = RoundedCornerShape(50.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }

        FloatingActionButton(
            onClick = { onChangeExpanded.invoke(expanded.not()) },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ignitedMidGreen,
                    shape = RoundedCornerShape(50.dp)
                ),
            shape = RoundedCornerShape(50.dp),
            contentColor = ignitedMidGreen,
            containerColor = componentBackground,
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun decideColor(description: String): Color {
    return when (description) {
        stringResource(R.string.nubank_balance) -> MaterialTheme.colorScheme.primary
        else -> customBackground
    }
}

fun filterTransactionsOnScreen(
    originalList: List<TransactionModel>,
    filter: String
) = originalList.filter { it.name.contains(filter, true) }.toMutableList()

suspend fun handleSnackBar(
    snackBarHostState: SnackbarHostState,
    context: Context,
    message: String
) {
    snackBarHostState.showSnackbar(
        message = message,
        actionLabel = context.getString(R.string.snackbar_confirmation_button),
        duration = SnackbarDuration.Long
    )
}

enum class SnackBarActionType {
    ADD,
    EDIT,
    DELETE;
}