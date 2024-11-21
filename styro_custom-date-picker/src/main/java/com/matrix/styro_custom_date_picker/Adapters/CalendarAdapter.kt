package com.matrix.styro_custom_date_picker.Adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.matrix.styro_custom_date_picker.DataHolders.CalendarSet
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysDisabledTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysHighlightTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarDaysTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.dayBackgroundDefault
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.font
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.dayHighlightBackground
import com.matrix.styro_custom_date_picker.R
import java.util.Calendar

/**
 * 20 Nov 2024
 * @author Saurav Sajeev
 */

class CalendarAdapter(
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
    private var unselColor: Int = 0

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        if (splitDate(highlight)[0] > calendarSet.lastDayOfMonth)
            highlight =
                "${calendarSet.lastDayOfMonth}-${calendarSet.dateSet[1]}-${calendarSet.dateSet[2]}"
        selected = calendarSet.dateSet.toMutableList()
        setCalendar()

        val view = p1 ?: LayoutInflater.from(context).inflate(R.layout.day, p2, false)
        val dayText =
            view.findViewById<LinearLayout>(R.id.day_lly).findViewById<TextView>(R.id.dayText)
        val today = upperLimit.split("-").map { Integer.valueOf(it) }
        var last = calendarSet.lastDayOfMonth
        dayText.typeface = ResourcesCompat.getFont(context, font)
        dayText.text = calendarSet.dates[p0]

        dayText.setOnClickListener {
            try {
                if (dayText.currentTextColor != selectedView.currentTextColor &&
                    dayText.currentTextColor != unselColor
                ) {

                    deselectAll()
                    selectedView.setTextColor(calendarDaysTextColor)
                    selectedView = it as TextView
                    selectedView.setTextColor(calendarDaysHighlightTextColor)
                    highlight = it.text as String
                    it.setBackgroundResource(R.drawable.calendar_highlight)
                    selected[0] = Integer.valueOf(it.text.toString())
                    setCalendar()

                }
            } catch (_: Exception) {

                if (dayText.currentTextColor != selectedView.currentTextColor &&
                    dayText.currentTextColor != unselColor
                ) {
                    deselectAll()
                    selectedView = it as TextView
                    selectedView.setTextColor(calendarDaysHighlightTextColor)
                    highlight = it.text as String
                    it.setBackgroundResource(R.drawable.calendar_highlight)
                    selected[0] = Integer.valueOf(it.text.toString())
                    setCalendar()
                }
            }
        }
        if (Integer.valueOf(calendarSet.dateSet[1]) == today[1]
            && Integer.valueOf(calendarSet.dateSet[2]) == today[2]
        )
            last = today[0]

        if (p0 + 1 > calendarSet.firstDay +
            (
                    if (
                        calendarSet.offset[0] > 0
                        && calendarSet.dateSet[1] == calendarSet.offset[1]
                        && calendarSet.dateSet[2] == calendarSet.offset[2]
                    ) calendarSet.offset[0] - 1 else 0
                    ) && p0 < last + calendarSet.firstDay
        ) {
            if (calendarSet.dates[p0] == splitDate(highlight)[0].toString() ||
                (splitDate(highlight)[0] > calendarSet.lastDayOfMonth && calendarSet.dates[p0] ==
                        calendarSet.lastDayOfMonth.toString())
            ) {
                selectedView = dayText
                dayText.setBackgroundResource(dayHighlightBackground)
                dayText.setTextColor(calendarDaysHighlightTextColor)
            } else
                dayText.setTextColor(calendarDaysTextColor)
        } else {
            dayText.setTextColor(calendarDaysDisabledTextColor)
            if (unselColor == 0)
                unselColor = dayText.currentTextColor
        }

        viewList.add(view)
        return view
    }

    private fun splitDate(date: String): List<Int> = date.split("-").map { Integer.valueOf(it) }

    private fun deselectAll() {
        for (i in viewList)
            i.findViewById<TextView>(R.id.dayText).setBackgroundResource(dayBackgroundDefault)
    }

    /** Returns the selected date from the calendar */
    companion object {
        private var selected = mutableListOf<Int>()
        private var calendar: Calendar = Calendar.getInstance()
        fun getSelected(): String {
            return "${selected[0]}-${selected[1]}-${selected[2]}"
        }

        private fun setCalendar() {
            calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, selected[2])
            calendar.set(Calendar.MONTH, selected[1] - 1)
            calendar.set(Calendar.DAY_OF_MONTH, selected[0])
        }

        fun getCalendar() = calendar
    }

}