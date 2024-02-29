package com.example.a4p3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter(private val onClick: (Int) -> Unit) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(current.id)
        }
        holder.bind(current)
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseDate: TextView = itemView.findViewById(R.id.expense_date)
        private val expenseAmount: TextView = itemView.findViewById(R.id.expense_amount)
        private val expenseCategory: TextView = itemView.findViewById(R.id.expense_category)
        fun bind(expense: Expense) {
            // Bind expense data to UI elements in itemView
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            expenseDate.text = dateFormat.format(expense.date)

            // Assuming amount is a Double, format it as needed
            expenseAmount.text = String.format("$%.2f", expense.amount)

            // Set the category text
            expenseCategory.text = expense.category
        }

        companion object {
            fun create(parent: ViewGroup): ExpenseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.expense_item, parent, false)
                return ExpenseViewHolder(view)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }
        }
    }
}

