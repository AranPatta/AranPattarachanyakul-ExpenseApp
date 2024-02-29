package com.example.a4p3

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ExpenseRepository private constructor(context: Context) {
    private val database = ExpenseDatabase.getDatabase(context)
    private val expenseDao = database.expenseDao()

    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
    fun getExpensesByCategory(category: String): Flow<List<Expense>> = expenseDao.getExpensesByCategory(category)
    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>> = expenseDao.getExpensesByDateRange(startDate, endDate)

    suspend fun insert(expense: Expense) = expenseDao.insert(expense)

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }

        fun get(): ExpenseRepository {
            return INSTANCE ?: throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }
}

