package com.matrix.styro_custom_date_picker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.matrix.styro_custom_date_picker.DataHolders.CalendarSet
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysDisabledTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysHighlightTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.dayBackgroundDefault
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.dayHighlightBackground
import com.matrix.styro_custom_date_picker.R
import java.util.Calendar

/**
 * 20 Nov 2024
 * @author Saurav Sajeev
 */

internal class CalendarAdapter(
    private val context: Context,
    private val calendarSet: CalendarSet,
    private var highlight: String,
    private var upperLimit: String
) : BaseAdapter() {
    private lateinit var selectedView: TextView
    private val viewList = mutableListOf<View>()
    override fun getCount() = calendarSet.dates.size
    override fun getItem(p0: Int): Any = calendarSet.dates[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    private var unselectedColor: Int = 0
    private val weekSelection = CustomCalendarResources.weekSelection
    private var activeCount = 0

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        selected = calendarSet.dateSet.toMutableList()

        if (splitDate(highlight)[0] > calendarSet.lastDayOfMonth)
            highlight = "${calendarSet.lastDayOfMonth}-${calendarSet.dateSet[1]}-${calendarSet.dateSet[2]}"
        else if (splitDate(highlight)[0] > splitDate(upperLimit)[0] &&
            splitDate(highlight)[1] >= splitDate(upperLimit)[1] &&
            splitDate(highlight)[2] >= splitDate(upperLimit)[2]
        ) {
            highlight = upperLimit
            selected = splitDate(highlight).toMutableList()
            calendarSet.dateSet = selected
        }

        activeCount = (calendarSet.monthDays.indexOf(
            upperLimit.split("-").map { it.toInt() }) - calendarSet.firstDay).let {
            if (it <= 0) calendarSet.lastDayOfMonth - 1 else it
        }


        setCalendar()

        val view = p1 ?: LayoutInflater.from(context).inflate(R.layout.day, p2, false)
        val dayText = view.findViewById<LinearLayout>(R.id.day_lly).findViewById<TextView>(R.id.dayText)

        if (calendarFontSize > 0)
            dayText.textSize = calendarFontSize

        dayText.typeface = ResourcesCompat.getFont(context, CustomCalendarResources.font)
        dayText.text = calendarSet.dates[p0]

        dayText.setOnClickListener {
            if (dayText.currentTextColor != selectedView.currentTextColor &&
                dayText.currentTextColor != unselectedColor
            ) {
                deselectAll()
                try {
                    selectedView.setTextColor(calendarDaysTextColor)
                } finally {
                    selectedView = it as TextView
                    selectedView.setTextColor(calendarDaysHighlightTextColor)

                    if (weekSelection)
                        highlightWeek(p0)

                    highlight = it.text as String
                    it.setBackgroundResource(dayHighlightBackground)
                    selected[0] = Integer.valueOf(it.text.toString())
                    setCalendar()
                }
            }
        }

        val highlightDay = splitDate(highlight)[0]
        val selectedStart = calendarSet.firstDay + (highlightDay - 1)
        val start = selectedStart - (selectedStart % 7)
        val selectedEnd = start + 6

        val monthStartIndex = calendarSet.firstDay
        val isInCurrentMonth = p0 in monthStartIndex..monthStartIndex + activeCount

        val isInSelectedWeek = weekSelection && p0 in start..selectedEnd

        if (isInCurrentMonth || isInSelectedWeek) {
            if (calendarSet.dates[p0] == splitDate(highlight)[0].toString() ||
                (splitDate(highlight)[0] > calendarSet.lastDayOfMonth &&
                        calendarSet.dates[p0] == calendarSet.lastDayOfMonth.toString())
            ) {
                selectedView = dayText
                dayText.setBackgroundResource(dayHighlightBackground)
                dayText.setTextColor(calendarDaysHighlightTextColor)
            } else if (isInSelectedWeek) {
                dayText.setBackgroundResource(dayHighlightBackground)
                dayText.setTextColor(calendarDaysHighlightTextColor)
                if (p0 == start) {
                    selectedWeek.clear()
                    for (i in start..selectedEnd) selectedWeek.add(calendarSet.monthDays[i])
                }
            } else {
                dayText.setTextColor(calendarDaysTextColor)
            }

            dayText.isClickable = true
            dayText.isEnabled = true

        } else {
            dayText.setTextColor(calendarDaysDisabledTextColor)
            dayText.isClickable = false
            dayText.isEnabled = false
            if (unselectedColor == 0)
                unselectedColor = dayText.currentTextColor
        }

        viewList.addIfAbsent(view)
        return view
    }

    fun MutableList<View>.addIfAbsent(view: View) {
        if (indexOf(view) == -1) add(view)
    }

    private fun highlightWeek(selectedIndex: Int) {
        try {
            if (selectedIndex < 0 || viewList.isEmpty()) return

            val startIndex = selectedIndex - (selectedIndex % 7)
            val endIndex = (startIndex + 6).coerceAtMost(viewList.size - 1)

            selectedWeek.clear()

            for (i in startIndex..endIndex) {
                val tv = viewList[i].findViewById<TextView>(R.id.dayText)
                selectedWeek.add(calendarSet.monthDays[i])
                tv.setBackgroundResource(dayHighlightBackground)
                tv.setTextColor(calendarDaysHighlightTextColor)
            }
        } catch (_: Exception) { }
    }

    private fun splitDate(date: String): List<Int> = date.split("-").map { Integer.valueOf(it) }

    private fun deselectAll() {
        for (i in 0 until viewList.size) {
            viewList[i].findViewById<TextView>(R.id.dayText).apply {
                setBackgroundResource(dayBackgroundDefault)
                if (i >= calendarSet.firstDay && i <= calendarSet.firstDay + activeCount)
                    setTextColor(calendarDaysTextColor)
                else
                    setTextColor(calendarDaysDisabledTextColor)
            }
        }
    }

    /** Returns the selected date from the calendar */
    internal companion object {
        private var selected = mutableListOf<Int>()
        private var selectedWeek = mutableListOf<List<Int>>()
        private var calendar: Calendar = Calendar.getInstance()
        fun getSelected() = "${selected[0]}-${selected[1]}-${selected[2]}"

        fun getSelectedWeek(): List<String> = selectedWeek.map { "${it[0]}-${it[1]}-${it[2]}" }

        private fun setCalendar() {
            calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, selected[2])
            calendar.set(Calendar.MONTH, selected[1] - 1)
            calendar.set(Calendar.DAY_OF_MONTH, selected[0])
        }
    }
}
