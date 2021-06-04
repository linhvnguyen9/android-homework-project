package com.linh.myapplication.presentation.result

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.linh.myapplication.databinding.FragmentScheduleFilterDialogBinding
import com.linh.myapplication.presentation.CalendarUtl
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class ScheduleFilterDialogFragment : DialogFragment() {
    private val viewModel: ScheduleViewModel by sharedViewModel()

    private lateinit var binding: FragmentScheduleFilterDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleFilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(1000, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textStartDate.text = CalendarUtl.toFormattedString(viewModel.timeLower)
        binding.textEndDate.text = CalendarUtl.toFormattedString(viewModel.timeUpper)

        binding.textStartDate.setOnClickListener {
            DatePickerDialog(requireContext(),
                { view, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    viewModel.timeLower = calendar.timeInMillis
                    binding.textStartDate.text = CalendarUtl.toFormattedString(viewModel.timeLower)
                    viewModel.getSchedule(null, null, viewModel.timeLower, null)
                },
                Calendar.getInstance().apply { timeInMillis = viewModel.timeLower }
                    .get(Calendar.YEAR),
                Calendar.getInstance().apply { timeInMillis = viewModel.timeLower }
                    .get(Calendar.MONTH),
                Calendar.getInstance().apply { timeInMillis = viewModel.timeLower }
                    .get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        binding.textEndDate.setOnClickListener {
            DatePickerDialog(requireContext(),
                { view, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    viewModel.timeUpper = calendar.timeInMillis
                    binding.textEndDate.text = CalendarUtl.toFormattedString(viewModel.timeUpper)
                    viewModel.getSchedule(null, null, null,  viewModel.timeUpper)
                },
                Calendar.getInstance().apply { timeInMillis = viewModel.timeUpper }
                    .get(Calendar.YEAR),
                Calendar.getInstance().apply { timeInMillis = viewModel.timeUpper }
                    .get(Calendar.MONTH),
                Calendar.getInstance().apply { timeInMillis = viewModel.timeUpper }
                    .get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.buttonDismiss.setOnClickListener {
            dismiss()
        }
    }
}