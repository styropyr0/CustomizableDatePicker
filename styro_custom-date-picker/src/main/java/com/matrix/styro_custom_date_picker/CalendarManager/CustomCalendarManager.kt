package com.matrix.styro_custom_date_picker.CalendarManager;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.matrix.styro_custom_date_picker.Adapters.CalendarAdapter
import com.matrix.styro_custom_date_picker.CalendarManager.DateManager.getDateArray
import com.matrix.styro_custom_date_picker.DataHolders.CalendarSet
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarBackgroundColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.yearDropDownBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.yearDropdownTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.font
import com.matrix.styro_custom_date_picker.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

/**
 * 20 Nov 2024
 * @author Saurav Sajeev
 **/

class CalendarManager(
    private val month: TextView,
    private val year: TextView,
    private val date: String = DateManager.today(),
    private val popupView: View,
    private val offset: List<Int> = listOf(0, 0, 2000),
    private val upperLimit: String = DateManager.today()
) {

    fun setUp(): CalendarSet = generateDaysForMonth()

    private fun generateDaysForMonth(): CalendarSet {
        val days = mutableListOf<String>()
        val date: List<Int> = getDateArray(date)
        SetDates.currentDate = date
        DateManager.offset = offset
        val calendar = Calendar.getInstance()
        calendar.set(date[2], date[1] - 1, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        var maxDaysInMonth = getDaysInMonth(date)
        var maxDaysPrevMonth = prevMonthDays(date)

        month.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        year.text = date[2].toString()

        if ((date[2] % 4 == 0 && date[2] % 100 != 0) || date[2] % 400 == 0) {
            if (date[1] == 2)
                maxDaysInMonth++
            if (date[1] == 3)
                maxDaysPrevMonth++
        }

        for (i in 0 until firstDayOfMonth) {
            days.add("${maxDaysPrevMonth - (firstDayOfMonth - (i + 1))}")
        }
        for (i in 1..maxDaysInMonth) {
            days.add(i.toString())
        }

        val limit = maxDaysInMonth + firstDayOfMonth
        for (i in 0 until if (limit > 35) 42 - limit else 35 - limit) {
            days.add("${i + 1}")
        }
        return CalendarSet(days, firstDayOfMonth, maxDaysInMonth, date, offset)
    }

    private fun prevMonthDays(date: List<Int>): Int {
        val eDate = mutableListOf(date[0], date[1] - 1, date[2])
        return getDaysInMonth(eDate)
    }

    private fun getDaysInMonth(date: List<Int>): Int {
        val month = if (date[1] > 12) 1 else if (date[1] < 1) 12 else date[1]
        val year = if (date[1] > 12) date[2] + 1 else if (date[1] < 1) date[2] - 1 else date[1]
        return YearMonth.of(year, month).lengthOfMonth()
    }

}

object DateManager {

    lateinit var offset: List<Int>
    private var upperLimit = today()

    /** Returns today's date */
    fun today(): String {
        val calendar = Calendar.getInstance()
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
    }

    /**Formats a date based on the format specified
     * @param date The date as a String in a valid date format.
     * @param currentFormat The current format of the date passed.
     * @param finalFormat The format to which the passed date needs to be converted to.**/
    fun formatDate(date: String, currentFormat: String, finalFormat: String): String {
        try {
            val outputFormatter = DateTimeFormatter.ofPattern(finalFormat.trim())
            val inputFormatter = DateTimeFormatter.ofPattern(currentFormat.trim())
            return LocalDate.parse(date, inputFormatter).format(outputFormatter)
        } catch (_: Exception) {
            return date
        }
    }

    /** Converts string into array of date values
     * @param date String date which needs to be converted**/
    fun getDateArray(date: String): List<Int> = date.split("-").map { Integer.valueOf(it) }

    /**Handles date selection*/
    fun adjustDate(
        context: Context,
        currentDate: MutableList<Int>,
        view: View,
        popupView: View,
        upperLimit: String
    ) {
        if (view == popupView.findViewById(R.id.year))
            setUpYearSelection(context, currentDate, view, popupView, upperLimit)
        else {
            if (view == popupView.findViewById(R.id.month_next))
                currentDate[1]++
            else if (view == popupView.findViewById(R.id.month_prev))
                currentDate[1]--

            val today = getDateArray(upperLimit).toMutableList()

            if (currentDate[2] == today[2]) {
                when {
                    currentDate[1] > today[1] -> currentDate[1] = 1
                    currentDate[1] < 1 -> currentDate[1] = today[1]
                }
            } else {
                when {
                    currentDate[1] > 12 -> currentDate[1] = 1
                    currentDate[1] < 1 -> currentDate[1] = 12
                }
            }
            if (currentDate[2] > today[2]) {
                currentDate[2] = today[2]
                currentDate[1] = today[1]
                currentDate[0] = today[0]
            }

            if (currentDate[1] < offset[1] && offset[1] > 0 && currentDate[2] == offset[2])
                currentDate[1] = today[1]

            //restrict choosing future months
            if ((currentDate[2] == today[2] &&
                        currentDate[1] <= today[1]) ||
                currentDate[2] < today[2]
            ) {
                setDate(context, currentDate, popupView, offset, upperLimit)
            } else if (currentDate[1] == 12) {
                currentDate[1] = today[1]
                setDate(context, currentDate, popupView, offset, upperLimit)
            } else {
                currentDate[1] = 1
                if (offset[1] > 0 && currentDate[2] == offset[2])
                    currentDate[1] = offset[1]
                setDate(context, currentDate, popupView, offset, upperLimit)
            }
        }
    }

    /**Handles year selection*/
    private fun setUpYearSelection(
        context: Context,
        currentDate: MutableList<Int>,
        view: View,
        popupView: View,
        upperLimit: String
    ) {
        DateManager.upperLimit = upperLimit
        val popupDimension = popupView.findViewById<LinearLayout>(R.id.year).width
        val dropdownView = LayoutInflater.from(context).inflate(R.layout.dropdown_container, null)
        dropdownView.setBackgroundResource(yearDropDownBackground)

        val popupWindow = PopupWindow(
            dropdownView, popupDimension / 2 + 35,
            popupDimension, true
        )

        val container: LinearLayout = dropdownView.findViewById(R.id.dropdownContainer)
        val currentYear = Integer.valueOf(upperLimit.split("-")[2])
        val items =
            Array(currentYear - if (offset[2] == 0) 2000 else offset[2] + 1) { currentYear - it }

        for (item in items) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.dropdown_menu, container, false)
            val itemText = itemView.findViewById<TextView>(R.id.menu_item_text)
            itemText.typeface = ResourcesCompat.getFont(context, font)
            itemText.setTextColor(yearDropdownTextColor)
            itemText.text = item.toString()
            itemView.setOnClickListener {
                popupView.findViewById<TextView>(R.id.year_text).text = itemText.text
                currentDate[2] = Integer.valueOf(item)

                //set the month to current highest when year is changed with a month higher than current month
                if (currentDate[1] > getDateArray(upperLimit)[1] &&
                    currentDate[2] == getDateArray(upperLimit)[2]
                ) {
                    currentDate[1] = getDateArray(upperLimit)[1]
                }
                setDate(context, currentDate, popupView, offset, upperLimit)
                popupWindow.dismiss()
            }
            container.addView(itemView)
        }

        popupWindow.showAsDropDown(view, popupDimension / 2 - 35, 10)
    }

    fun setDate(
        context: Context,
        currentDate: List<Int>,
        popupView: View,
        offset: List<Int> = listOf(0, 0, -1),
        upperLimit: String
    ) {
        val month: TextView = popupView.findViewById(R.id.month)
        val year: TextView = popupView.findViewById(R.id.year_text)
        val calendarGridView: GridView = popupView.findViewById(R.id.calendar)


        val date = LocalDate.parse(
            "${currentDate[0]}-${currentDate[1]}-${currentDate[2]}",
            DateTimeFormatter.ofPattern("d-M-yyyy")
        ).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val calendarSet = CalendarManager(month, year, date, popupView, offset).setUp()

        calendarGridView.setBackgroundColor(calendarBackgroundColor)
        calendarGridView.adapter = CalendarAdapter(
            context,
            calendarSet,
            date,
            upperLimit
        )
    }

}

object SetDates {
    lateinit var currentDate: List<Int>
}
