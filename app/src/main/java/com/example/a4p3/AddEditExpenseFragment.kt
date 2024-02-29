package com.example.a4p3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a4p3.R
import com.example.a4p3.databinding.FragmentAddEditExpenseBinding
import java.util.*


class AddEditExpenseFragment : Fragment() {

    private var _binding: FragmentAddEditExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expenseViewModel: ExpenseViewModel

    private val args: AddEditExpenseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        val expenseId = args.expenseId

        binding.categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Food", "Entertainment", "Housing", "Utilities", "Fuel", "Automotive", "Misc")
        )

        binding.saveButton.setOnClickListener {
            val amountText = binding.amountEditText.text.toString()
            val amount = if (amountText.isNotEmpty()) amountText.toDouble() else 0.0

            val newExpense = Expense(
                id = if (expenseId == -1) 0 else expenseId,
                date = Date(),
                amount = binding.amountEditText.text.toString().toDouble(),
                category = binding.categorySpinner.selectedItem.toString(),
            )
            saveExpense(newExpense)
            findNavController().popBackStack()
        }
    }


    private fun saveExpense(expense: Expense) {
        if (expense.id == 0) {
            expenseViewModel.insert(expense)
        } else {
            expenseViewModel.update(expense)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
