package com.example.a4p3

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date


class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val expenseDao: ExpenseDao = ExpenseDatabase.getDatabase(application).expenseDao()

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses().asLiveData()

    fun insert(expense: Expense) = viewModelScope.launch {
        expenseDao.insert(expense)
    }

    fun getExpensesByDateRange(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            expenseDao.getExpensesByDateRange(startDate, endDate).collect { expenses ->
                _expenses.postValue(expenses)
            }
        }
    }

    fun getExpensesByCategory(category: String){
        viewModelScope.launch {
            expenseDao.getExpensesByCategory(category).collect { expensesList ->
                _expenses.postValue(expensesList)
            }
        }
    }

    fun update(expense: Expense) = viewModelScope.launch {
        expenseDao.update(expense)
    }

}


