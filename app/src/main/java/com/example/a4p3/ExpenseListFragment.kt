package com.example.a4p3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4p3.databinding.FragmentExpenseListBinding

class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView with its adapter and layout manager
        val adapter = ExpenseAdapter { expenseId ->
            val direction = ExpenseListFragmentDirections.actionExpenseListFragmentToAddEditExpenseFragment(expenseId)
            findNavController().navigate(direction)
        }
        binding.expensesRecyclerview.adapter = adapter // Make sure the ID matches your layout XML
        binding.expensesRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        // Observe changes in the list of expenses and update the adapter
        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            adapter.submitList(expenses)
        }

        binding.addExpenseFab.setOnClickListener {
            findNavController().navigate(ExpenseListFragmentDirections.actionExpenseListFragmentToAddEditExpenseFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
