package com.example.a4p3

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val expenseDao: ExpenseDao = ExpenseDatabase.getDatabase(application).expenseDao()

    // Using LiveData to observe changes. Convert Flow to LiveData
    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses().asLiveData()

    fun insert(expense: Expense) = viewModelScope.launch {
        expenseDao.insert(expense)
    }

    fun loadExpense(expenseId: Int): LiveData<Expense> {
        return expenseDao.getExpenseById(expenseId).asLiveData()
    }

    fun update(expense: Expense) = viewModelScope.launch {
        expenseDao.update(expense)
    }

}


