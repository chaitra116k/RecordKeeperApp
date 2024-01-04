package com.example.recordkeeperapplication.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeperapplication.databinding.FragmentCyclingBinding
import com.example.recordkeeperapplication.editRecord.EditRecordActivity
import com.example.recordkeeperapplication.editRecord.INTENT_EXTRA_SCREEN_DATA

class CyclingFragment : Fragment() {
    private lateinit var binding: FragmentCyclingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCyclingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListener()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        binding.textViewLongesrRideValue.text = runningPreferences.getString("Longest Ride record",null)
        binding.longestRideDate.text = runningPreferences.getString("Longest Ride date",null)
        //binding.textview.text = runningPreferences.getString("Longest Ride date",null)
        binding.textViewBestSpeedValue.text = runningPreferences.getString("Best Speed record",null)
        binding.textViewBestSpeedDate.text = runningPreferences.getString("Best Speed date",null)
        binding.textViewBiggestClimbValue.text = runningPreferences.getString("BiggestClimb record",null)
        binding.textViewBiggestClimbDate.text = runningPreferences.getString("BiggestClimb date",null)
    }

    private fun setUpClickListener() {
        binding.container10km.setOnClickListener { launchCyclingRecordScreen("BiggestClimb") }
        binding.containerLongestRide.setOnClickListener { launchCyclingRecordScreen("Longest Ride") }
        binding.containerBestSpeed.setOnClickListener { launchCyclingRecordScreen("Best Speed") }
    }

    private fun launchCyclingRecordScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(distance, FILENAME,"Time"))
        startActivity(intent)
    }
    companion object{
        const val FILENAME = "cycling"
    }
}