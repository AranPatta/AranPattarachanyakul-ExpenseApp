package com.example.a4p3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a4p3.R
import com.example.a4p3.databinding.FragmentAddEditExpenseBinding
import java.util.*

class AddEditExpenseFragment : Fragment() {

    private var _binding: FragmentAddEditExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expenseViewModel: ExpenseViewModel

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


        binding.categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Food", "Entertainment", "Housing", "Utilities", "Fuel", "Automotive", "Misc")
        )

        binding.saveButton.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val amount = binding.amountEditText.text.toString().toDoubleOrNull()
        val category = binding.categorySpinner.selectedItem.toString()
        if (amount != null) {
            val expense = Expense(0, Date(), amount, category)
            expenseViewModel.insert(expense)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
