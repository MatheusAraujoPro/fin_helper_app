package com.example.fin_helper_app.presentation.summary

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.presentation.summary.bottomsheet.AddTransactionBottomSheet
import com.example.fin_helper_app.presentation.summary.bottomsheet.EditTransactionBottomSheet
import com.example.fin_helper_app.presentation.summary.components.BackgroundWrapper
import com.example.fin_helper_app.presentation.summary.components.FloatingOrbitalMenu
import com.example.fin_helper_app.presentation.summary.components.SnackBarActionType
import com.example.fin_helper_app.presentation.summary.components.TransactionsCardList
import com.example.fin_helper_app.presentation.summary.components.decideSnackBarMessage
import com.example.fin_helper_app.presentation.summary.components.filterTransactionsOnScreen
import com.example.fin_helper_app.presentation.summary.components.handleSnackBar
import com.example.fin_helper_app.ui.components.SearchInputField
import com.example.fin_helper_app.ui.components.TopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(viewModel: SummaryViewModel) {
    val state = viewModel.state.collectAsState()
    val action = viewModel::dispatcherAction
    val addBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val editBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var incomeTypeSelected by remember { mutableIntStateOf(0) }
    var inputText by remember { mutableStateOf("") }
    var filteredTransactions by remember { mutableStateOf(mutableListOf<TransactionModel>()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var floatOrbitalAreaExpanded by remember { mutableStateOf(false) }
    var transactionToEdit by remember { mutableStateOf(TransactionModel()) }


    Scaffold(
        topBar = {
            TopBar(
                headerTypeMap = state.value.headerCardTypeMap,
                transactionsListSize = state.value.transactionsList.size,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        },
        content = { paddingValues ->
            BackgroundWrapper(
                isLoading = state.value.isLoading,
                content = {
                    Column(
                        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        SearchInputField(
                            inputText = inputText,
                            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                            onChange = {
                                inputText = it
                                filteredTransactions =
                                    filterTransactionsOnScreen(
                                        originalList = state.value.transactionsList,
                                        filter = it
                                    )
                            },
                            onClickButton = {
                                filteredTransactions =
                                    filterTransactionsOnScreen(
                                        originalList = state.value.transactionsList,
                                        filter = inputText
                                    )
                            },
                        )

                        TransactionsCardList(
                            transactions = state.value.transactionsList,
                            filteredTransactions = filteredTransactions,
                            onCardTap = {
                                transactionToEdit = it
                                action(SummaryScreenAction.ShouldShowEditBottomSheet)
                            },
                            onDelete = { transactionId, transactionType ->
                                action(SummaryScreenAction.DeleteTransactionById(transactionId))
                                filteredTransactions = mutableListOf()
                                scope.launch {
                                    delay(500L)
                                    handleSnackBar(
                                        snackBarHostState = snackBarHostState,
                                        context = context,
                                        message = decideSnackBarMessage(
                                            transactionType = transactionType,
                                            context = context,
                                            actionType = SnackBarActionType.DELETE
                                        )
                                    )
                                }
                            }
                        )

                        if (state.value.shouldShowBottomSheet)
                            AddTransactionBottomSheet(
                                bottomSheetState = addBottomSheetState,
                                incomeType = IncomeType.value(incomeTypeSelected)!!,
                                onActionClick = {
                                    action(SummaryScreenAction.SaveTransaction(model = it))
                                    keyboardController?.hide()
                                    scope.launch {
                                        addBottomSheetState.hide()
                                        delay(500L)
                                        it.type.let { type ->
                                            handleSnackBar(
                                                context = context,
                                                snackBarHostState = snackBarHostState,
                                                message = decideSnackBarMessage(
                                                    transactionType = type,
                                                    context = context,
                                                    actionType = SnackBarActionType.ADD
                                                )
                                            )
                                        }
                                    }
                                },
                                onCloseClick = {
                                    action(SummaryScreenAction.ShouldShowBottomSheet)
                                    scope.launch {
                                        addBottomSheetState.hide()
                                    }
                                }
                            )

                        if (state.value.shouldShowEditBottomSheet)
                            EditTransactionBottomSheet(
                                bottomSheetState = addBottomSheetState,
                                transaction = transactionToEdit,
                                onActionClick = {
                                    action(SummaryScreenAction.EditTransaction(model = it))
                                    keyboardController?.hide()
                                    scope.launch {
                                        editBottomSheetState.hide()
                                        delay(500L)
                                        it.type.let { type ->
                                            handleSnackBar(
                                                context = context,
                                                snackBarHostState = snackBarHostState,
                                                message = decideSnackBarMessage(
                                                    transactionType = type,
                                                    context = context,
                                                    actionType = SnackBarActionType.EDIT
                                                )
                                            )
                                        }
                                    }
                                },
                                onCloseClick = {
                                    action(SummaryScreenAction.ShouldShowEditBottomSheet)
                                    scope.launch {
                                        editBottomSheetState.hide()
                                    }
                                }
                            )
                    }
                }

            )
        },
        floatingActionButton = {
            FloatingOrbitalMenu(
                expanded = floatOrbitalAreaExpanded,
                onChangeIncomeTypeValue = { incomeType ->
                    incomeTypeSelected = incomeType.value
                    action(SummaryScreenAction.ShouldShowBottomSheet)
                    floatOrbitalAreaExpanded = floatOrbitalAreaExpanded.not()
                },
                onChangeExpanded = { expanded ->
                    floatOrbitalAreaExpanded = expanded
                }
            )
        }
    )
}