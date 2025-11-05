package com.matrix.testproject

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrix.styro_custom_date_picker.CustomizableDatePicker
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    fun showDatePicker(v: View) {
        CustomCalendarResources.setCalendarDaysHighlightTextColor(Color.RED)
            .setDaysBarVisibility(View.VISIBLE)
            .setButtonDrawable(R.drawable.bg)

        val cv = CustomizableDatePicker()
        cv.show(this, "20-10-2023") {
            (v as Button).text = "Selected date: ${cv.getSelectedDate()}"
        }
    }

    @SuppressLint("SetTextI18n")
    fun showWeekPicker(v: View) {
        val cv = CustomizableDatePicker()
        cv.show(this, "20-10-2023", weekSelection = true) {
            (v as Button).text = "Selected week: ${cv.getSelectedWeek().joinToString(", ") { it }}"
        }
    }
}