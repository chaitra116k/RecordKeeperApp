package com.example.recordkeeperapplication.editRecord

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.recordkeeperapplication.R
import com.example.recordkeeperapplication.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

class EditRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRecordBinding

    @Suppress("DEPRECATION")
    private val screenData: ScreenData by lazy {
        //intent.getSerializableExtra("screen_data")as ScreenData
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                INTENT_EXTRA_SCREEN_DATA,
                ScreenData::class.java
            ) as ScreenData
        } else {
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
        }
    }
    private val recordPref by lazy {
        getSharedPreferences(
            screenData.sharedPreferencesName,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.green_700
                )
            )
        )
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "${screenData.record} Record"

//        val applicationPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        applicationPreferences.edit {
//            putString(
//                "some appli data","Appl preference val here"
//            )
//        }
//
//        val activityPref = getPreferences(Context.MODE_PRIVATE)
//        activityPref.edit {
//            putInt("some activity data",15)
//        }
//
//        val fileNamePrefe = getSharedPreferences("some shatred pref file name",Context.MODE_PRIVATE)
//        fileNamePrefe.edit {
//            putBoolean("some shared pref file name data",false)
//        }
        displayRecord()
        setUpUi()
    }

    private fun setUpUi() {
        binding.textInputRecord.hint = screenData.recordFieldHint
        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }
        binding.buttonClear.setOnClickListener {
            clearRecord()
            finish()
        }
    }

    private fun displayRecord() {
        binding.editTextRecord.setText(
            recordPref.getString(
                "${screenData.record} $sharedPreferencesRecordKey",
                null
            )
        )
        binding.editTextDate.setText(
            recordPref.getString(
                "${screenData.record} $sharedPreferencesDateKey",
                null
            )
        )
    }

    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        recordPref.edit {
            putString(
                "${this@EditRecordActivity.screenData.record} $sharedPreferencesRecordKey",
                record
            )
            putString(
                "${this@EditRecordActivity.screenData.record} $sharedPreferencesDateKey",
                date
            )
        }
    }

    private fun clearRecord() {
        recordPref.edit {
            remove("${screenData.record} $sharedPreferencesRecordKey")
            remove("${screenData.record} $sharedPreferencesDateKey")
        }
    }

    companion object {
        const val sharedPreferencesRecordKey = "record"
        const val sharedPreferencesDateKey = "date"
    }

    data class ScreenData(
        val record: String,
        val sharedPreferencesName: String,
        val recordFieldHint: String
    ) : Serializable
}