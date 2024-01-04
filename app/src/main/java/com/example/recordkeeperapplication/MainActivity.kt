package com.example.recordkeeperapplication

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.example.recordkeeperapplication.cycling.CyclingFragment
import com.example.recordkeeperapplication.databinding.ActivityMainBinding
import com.example.recordkeeperapplication.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.green_700)))

                binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.bottomNav.setOnItemSelectedListener(this)
//        binding.buttonRunning.setOnClickListener { runningButtonClicked() }
//        binding.buttonCycling.setOnClickListener { cyclingButtonClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickHandled = when (item.itemId) {
            R.id.reset_running -> {
                confirmationDialog(RUNNING_DISPLAY_VALUE)
                true
            }

            R.id.reset_cycling -> {
                confirmationDialog(CYCLING_DISPLAY_VALUE)
                true
            }

            R.id.reset_all -> {
                confirmationDialog(ALL_DISPLAY_VALUE)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

        return menuClickHandled
    }

    private fun confirmationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection records")
            .setMessage("Are you sure you want to clear the records?")
            .setPositiveButton("Yes") { _, _ ->
                when (selection) {
                    ALL_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CyclingFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    RUNNING_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    CYCLING_DISPLAY_VALUE-> {
                        getSharedPreferences(CyclingFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                }
                Snackbar.make(binding.frameContent, "Record clear successfully!",Snackbar.LENGTH_LONG).show()
                refreshCurentFragment()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun refreshCurentFragment() {
        when (binding.bottomNav.selectedItemId) {
            R.id.nav_running -> runningButtonClicked()
            R.id.nav_cycling -> cyclingButtonClicked()
            else -> {}
        }
    }


    private fun runningButtonClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
        return true
    }

    private fun cyclingButtonClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.nav_running -> runningButtonClicked()
            R.id.nav_cycling -> cyclingButtonClicked()
            else -> false
        }

    companion object{
        const val RUNNING_DISPLAY_VALUE = "running"
        const val CYCLING_DISPLAY_VALUE = "cycling"
        const val ALL_DISPLAY_VALUE = "all"
    }
}