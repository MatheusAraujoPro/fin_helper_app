package com.example.fin_helper_app.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.fin_helper_app.data.datasource.local.DataLocalDataSource
import com.example.fin_helper_app.data.repository.TransactionRepositoryImpl
import com.example.fin_helper_app.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import data.sqldelight.database.Transactions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideDataBaseDriver(@ApplicationContext appContext: Context): Transactions {
        val driver: SqlDriver =
            AndroidSqliteDriver(Transactions.Schema, appContext, "transactions.db")
        val database = Transactions(driver)
        return database
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Singleton
    @Provides
    fun provideTransactionRepository(transactionsDb: Transactions): TransactionRepository {
        return TransactionRepositoryImpl(
            DataLocalDataSource(transactionsDb)
        )
    }
}