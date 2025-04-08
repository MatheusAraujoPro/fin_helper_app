package com.example.fin_helper_app.presentation.summary.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fin_helper_app.R
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.helper.getDayMonthAndYear
import com.example.fin_helper_app.ui.components.CustomTextField
import com.example.fin_helper_app.ui.components.TextFieldType
import com.example.fin_helper_app.ui.components.TransactionType
import com.example.fin_helper_app.ui.theme.componentBackground
import com.example.fin_helper_app.ui.theme.ignitedBrandRed
import com.example.fin_helper_app.ui.theme.ignitedMidGreen
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(
    bottomSheetState: SheetState,
    incomeType: IncomeType,
    onActionClick: (transaction: TransactionModel) -> Unit,
    onCloseClick: () -> Unit
) {
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = Color.Black,
        onDismissRequest = {
            onCloseClick.invoke()
        }
    ) {
        BottomSheetContent(
            incomeType = incomeType,
            onActionClick = onActionClick,
            onCloseClick = onCloseClick
        )
    }
}

@Composable
private fun BottomSheetContent(
    incomeType: IncomeType,
    onActionClick: (transaction: TransactionModel) -> Unit,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        BottomSheetHeader(
            onCloseClick = onCloseClick
        )
        Spacer(modifier = Modifier.height(32.dp))
        BottomSheetForm(
            incomeType = incomeType,
            onActionClick = onActionClick,
        )
    }
}

@Composable
fun BottomSheetForm(
    incomeType: IncomeType,
    onActionClick: (transaction: TransactionModel) -> Unit
) {
    var descriptionText by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf(TransactionType.REVENUE) }
    var buttonSelected by remember { mutableIntStateOf(transactionType.value) }
    val createdAt = Date().getDayMonthAndYear()

    CustomTextField(
        inputText = incomeType.description,
        placeholder = "",
        isEnable = false,
        onChange = {}
    )
    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        inputText = descriptionText,
        placeholder = stringResource(R.string.description_placehoulder),
        onChange = {
            descriptionText = it
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    CustomTextField(
        inputText = priceText,
        placeholder = stringResource(R.string.price_placeholder),
        textFieldType = TextFieldType.NUMBER,
        onChange = {
            priceText = it
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    CategoryButtons(
        buttonSelected = buttonSelected,
        revenueButtonOnClick = {
            transactionType = TransactionType.REVENUE
        },
        expensiveButtonOnclick = {
            transactionType = TransactionType.EXPENSE
        },
        onChangeButtonSelect = {
            buttonSelected = it
        }
    )
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonColors(
            containerColor = ignitedMidGreen,
            contentColor = Color.White,
            disabledContainerColor = ignitedMidGreen.copy(alpha = 0.5f),
            disabledContentColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnable(descriptionText = descriptionText, priceText = priceText),
        onClick = {
            val transactionModel = TransactionModel(
                name = descriptionText,
                value = priceText.toDouble(),
                type = transactionType,
                createdAt = createdAt,
                incomeType = incomeType
            )
            onActionClick.invoke(
                transactionModel
            )
        }
    ) {
        Text(
            text = stringResource(R.string.bottom_sheet_action_button_title),
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun BottomSheetHeader(
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.new_transaction),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    onCloseClick.invoke()
                }
                .size(32.dp),
        )
    }
}

@Composable
private fun CategoryButtons(
    buttonSelected: Int,
    expensiveButtonOnclick: () -> Unit,
    revenueButtonOnClick: () -> Unit,
    onChangeButtonSelect: (value: Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .weight(0.5f),
            colors = ButtonColors(
                containerColor = componentBackground,
                contentColor = Color.White,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (buttonSelected == TransactionType.REVENUE.value) ignitedMidGreen else Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                revenueButtonOnClick.invoke()
                onChangeButtonSelect.invoke(TransactionType.REVENUE.value)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.revenue_text)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    tint = ignitedMidGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        Button(
            modifier = Modifier
                .weight(0.5f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = componentBackground,
                contentColor = Color.White,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (buttonSelected == TransactionType.EXPENSE.value) ignitedMidGreen else Color.Transparent
            ),
            onClick = {
                expensiveButtonOnclick.invoke()
                onChangeButtonSelect.invoke(TransactionType.EXPENSE.value)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.expense_text)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = ignitedBrandRed,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private fun isEnable(descriptionText: String, priceText: String) =
    descriptionText.isNotEmpty() && priceText.isNotEmpty()