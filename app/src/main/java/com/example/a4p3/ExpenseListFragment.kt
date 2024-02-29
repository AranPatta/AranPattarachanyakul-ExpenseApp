package com.example.a4p3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4p3.databinding.FragmentExpenseListBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()

    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExpenseAdapter { expenseId ->
            val direction = ExpenseListFragmentDirections.actionExpenseListFragmentToAddEditExpenseFragment(expenseId)
            findNavController().navigate(direction)
        }
        binding.expensesRecyclerview.adapter = adapter
        binding.expensesRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        expenseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ExpenseViewModel::class.java)

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            adapter.submitList(expenses)
        }

        binding.buttonAll.setOnClickListener {
            viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
                adapter.submitList(expenses)
            }
        }

        binding.buttonFood.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Food")
        }

        binding.buttonEntertainment.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Entertainment")
        }

        binding.buttonHousing.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Housing")
        }

        binding.buttonUtilities.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Utilities")
        }

        binding.buttonFuel.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Fuel")
        }

        binding.buttonAutomotive.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Automotive")
        }

        binding.buttonMisc.setOnClickListener {
            expenseViewModel.getExpensesByCategory("Misc")
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


        binding.filterButton.setOnClickListener {
            val startDateStr = binding.startDateEditText.text.toString()
            val endDateStr = binding.endDateEditText.text.toString()

            val startDate: Date? = try {
                if (startDateStr.isNotEmpty()) dateFormat.parse(startDateStr) else null
            } catch (e: ParseException) {
                null
            }

            val endDate: Date? = try {
                if (endDateStr.isNotEmpty()) dateFormat.parse(endDateStr) else null
            } catch (e: ParseException) {
                null
            }

            if (startDate != null && endDate != null) {
                expenseViewModel.getExpensesByDateRange(startDate, endDate)
            }
        }

        expenseViewModel.expenses.observe(viewLifecycleOwner) { expensesList ->
            adapter.submitList(expensesList)
        }

        binding.addExpenseFab.setOnClickListener {
            findNavController().navigate(ExpenseListFragmentDirections.actionExpenseListFragmentToAddEditExpenseFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
