package com.example.fin_helper_app.presentation.summary

import androidx.lifecycle.ViewModel
import com.example.fin_helper_app.domain.enums.IncomeType
import com.example.fin_helper_app.domain.model.TransactionModel
import com.example.fin_helper_app.domain.usecase.DeleteTransactionByIdUseCase
import com.example.fin_helper_app.domain.usecase.EditTransactionUseCase
import com.example.fin_helper_app.domain.usecase.GetTransactionsUseCase
import com.example.fin_helper_app.domain.usecase.StoreTransactionUseCase
import com.example.fin_helper_app.ui.components.TransactionType
import com.example.fin_helper_app.ui.model.HeaderCardType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val storeTransactionUseCase: StoreTransactionUseCase,
    private val editTransactionUseCase: EditTransactionUseCase,
    private val deleteTransactionByIdUseCase: DeleteTransactionByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SummaryScreenState())

    val state: StateFlow<SummaryScreenState>
        get() = _state

    init {
        getTransactions()
    }

    fun dispatcherAction(action: SummaryScreenAction) {
        when (action) {
            is SummaryScreenAction.SaveTransaction -> saveTransaction(action.model)
            is SummaryScreenAction.DeleteTransactionById -> deleteById(action.transactionId)
            is SummaryScreenAction.EditTransaction -> editTransaction(action.model)

            SummaryScreenAction.GetTransactions -> getTransactions()
            SummaryScreenAction.ShouldShowBottomSheet -> shouldShowBottomSheet()
        }
    }

    private fun shouldShowBottomSheet() {
        val showBottomSheet = _state.value.shouldShowBottomSheet
        _state.value = _state.value.copy(shouldShowBottomSheet = showBottomSheet.not())
    }

    private fun getTransactions() {
        _state.value = _state.value.copy(
            isLoading = true,
        )
        getTransactionsUseCase(
            onSuccess = {
                _state.value = _state.value.copy(
                    transactionsList = it,
                    isLoading = false
                )
                segmentValuesPerIncomeType(it)
            },
            onError = {
                _state.value = _state.value.copy(
                    error = it,
                    isLoading = false
                )
            }
        )
    }

    private fun deleteById(id: Long) {
        _state.value = _state.value.copy(
            isLoading = true,
            transactionsList = listOf()
        )

        deleteTransactionByIdUseCase(
            DeleteTransactionByIdUseCase.Params(
                id = id
            ),
            onSuccess = {
                _state.value = _state.value.copy(
                    isLoading = false
                )
                getTransactions()
            }
        )
    }

    private fun saveTransaction(model: TransactionModel) {
        _state.value = _state.value.copy(
            isLoading = false,
            transactionsList = listOf()
        )
        storeTransactionUseCase(
            StoreTransactionUseCase.Params(
                model = model
            ),
            onSuccess = {
                _state.value = _state.value.copy(
                    isLoading = false,
                    shouldShowBottomSheet = _state.value.shouldShowBottomSheet.not()
                )
                getTransactions()
            },
            onError = {
                _state.value = state.value.copy(
                    error = it,
                    isLoading = false
                )
            }
        )
    }

    private fun editTransaction(model: TransactionModel) {
        _state.value = _state.value.copy(
            isLoading = true,
            transactionsList = listOf()
        )

        editTransactionUseCase(
            EditTransactionUseCase.Params(
                model = model
            ),
            onSuccess = {
                _state.value = _state.value.copy(
                    isLoading = false
                )
                getTransactions()
            }
        )
    }

    private fun segmentValuesPerIncomeType(transactionsList: List<TransactionModel>) {
        val nubankTotal =
            transactionsList.filter { it.incomeType == IncomeType.NUBANK }.sumOf { it.value }

        val genialTotal =
            transactionsList.filter { it.incomeType == IncomeType.GENIAL }
                .sumOf { it.value }

        val balanceRevenue =
            transactionsList.filter { it.incomeType == IncomeType.BALANCE && it.type == TransactionType.REVENUE }
                .sumOf { it.value }

        val balanceExpense =
            transactionsList.filter { it.incomeType == IncomeType.BALANCE && it.type == TransactionType.EXPENSE }
                .sumOf { it.value }

        val totalExpenses = balanceExpense + nubankTotal + genialTotal

        val realBalance = balanceRevenue - totalExpenses

        _state.value = _state.value.copy(
            headerCardTypeMap = mapOf(
                Pair(
                    HeaderCardType.BALANCE,
                    realBalance
                ),
                Pair(
                    HeaderCardType.NUBANK_CARD,
                    nubankTotal
                ),
                Pair(
                    HeaderCardType.GENIAL_CARD,
                    genialTotal
                )
            )
        )
    }
}