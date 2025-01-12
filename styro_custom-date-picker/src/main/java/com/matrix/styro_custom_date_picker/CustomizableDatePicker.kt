package com.matrix.styro_custom_date_picker

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.matrix.styro_custom_date_picker.Adapters.CalendarAdapter
import com.matrix.styro_custom_date_picker.CalendarManager.DateManager
import com.matrix.styro_custom_date_picker.CalendarManager.DateManager.today
import com.matrix.styro_custom_date_picker.CalendarManager.SetDates.currentDate
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonDrawable
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitle
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.daysBarVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.drawerColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.drawerVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.monthSwitchIconLeft
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.monthSwitchIconRight
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.popupBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarBackgroundColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.font
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.gestureMonthSwitching
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarDaysBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarDefaultDayColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarSundayColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.yearSwitchIcon
import com.matrix.styro_custom_date_picker.Enums.MotionDirection
import com.matrix.styro_custom_date_picker.Utils.SwipeDetector
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author Saurav Sajeev
 * @date 2024-11-20
 * @see [CustomizableDatePicker](https://github.com/styropyr0/CustomizableDatePicker) for details
 */


class CustomizableDatePicker {
    private lateinit var c: Context
    private lateinit var popup: View
    private var upperLimit = today()
    private var selectedDate = today()

    /**Call show() of CustomizableDatePicker to show DatePicker:
     * @param context The context
     * @param date The date in dd-MM-yyyy format
     * @param displayView A text view or a button whose text shall be updated with the date from the date picker
     * @param offset The offset from which the date picker shall show the dates. If not passed, the default value is from 2000.
     * @param upperLimit If set, the date picker will let the user to select dates until this date. Default is the current day's date.
     * @param callback The function which you might want to get executed after selecting the date (Optional)
     * **/

    @SuppressLint("ClickableViewAccessibility")
    fun show(
        context: Context,
        date: String,
        displayView: View? = null,
        offset: String = "0-0-0",
        upperLimit: String = today(),
        todo: () -> Unit = {}
    ) {
        this.upperLimit = upperLimit
        c = context
        val layoutInflater = LayoutInflater.from(context)
        popup = layoutInflater.inflate(R.layout.calendar, null)
        popup.setBackgroundResource(popupBackground)
        popup.findViewById<LinearLayout>(R.id.monthSwitcher).setBackgroundResource(
            selectorBackground
        )
        popup.findViewById<LinearLayout>(R.id.year).setBackgroundResource(
            selectorBackground
        )

        popup.findViewById<TextView>(R.id.month).setTextColor(selectorTextColor)
        popup.findViewById<TextView>(R.id.year_text).setTextColor(selectorTextColor)
        if (selectorFontSize > 0) {
            popup.findViewById<TextView>(R.id.month).textSize = selectorFontSize
            popup.findViewById<TextView>(R.id.year_text).textSize = selectorFontSize
        }

        popup.findViewById<ImageView>(R.id.month_next).setImageResource(monthSwitchIconRight)
        popup.findViewById<ImageView>(R.id.month_prev).setImageResource(monthSwitchIconLeft)
        popup.findViewById<ImageView>(R.id.year_icon).setImageResource(yearSwitchIcon)

        val defaultFont = ResourcesCompat.getFont(context, font)
        popup.findViewById<TextView>(R.id.month).typeface = defaultFont
        popup.findViewById<TextView>(R.id.year_text).typeface = defaultFont


        val title = popup.findViewById<TextView>(R.id.title)
        if (datePickerTitleVisibility == View.VISIBLE && datePickerTitle.isNotEmpty()) {
            title.typeface = defaultFont
            title.text = datePickerTitle
            title.visibility = datePickerTitleVisibility
            title.setTextColor(datePickerTitleColor)
            if (datePickerTitleFontSize > 0)
                title.textSize = datePickerTitleFontSize
        } else title.visibility = View.GONE

        if (drawerVisibility == View.VISIBLE) {
            val wrappedDrawable =
                ResourcesCompat.getDrawable(context.resources, R.drawable.slider_bg, null)
            wrappedDrawable?.setTint(drawerColor)
            popup.findViewById<ImageView>(R.id.drawer).setImageDrawable(wrappedDrawable)
        }
        popup.findViewById<ImageView>(R.id.drawer).visibility = drawerVisibility

        var dateL = DateManager.getDateArray(date)
        val upperDateL = DateManager.getDateArray(upperLimit)
        if (dateL[2] > upperDateL[2])
            dateL = upperDateL
        else if (dateL[2] == upperDateL[2]) {
            if (dateL[1] > upperDateL[1])
                dateL = upperDateL
            else if (dateL[1] == upperDateL[1] && dateL[0] > upperDateL[0])
                dateL = upperDateL
        }
        val off = DateManager.getDateArray(offset)
        popup.findViewById<ImageView>(R.id.month_next).setOnClickListener {
            switchMonth(it, MotionDirection.TO_LEFT.ordinal)
        }
        popup.findViewById<ImageView>(R.id.month_prev).setOnClickListener {
            switchMonth(it, MotionDirection.TO_RIGHT.ordinal)
        }
        popup.findViewById<LinearLayout>(R.id.year).setOnClickListener {
            yearDropdown(it)
        }

        val chooseButton: LinearLayout = popup.findViewById(R.id.calendar_choose)
        val chooseText: TextView = popup.findViewById(R.id.choose_text)
        chooseButton.setBackgroundResource(buttonDrawable)
        chooseText.setTextColor(buttonTextColor)
        if (buttonFontSize > 0)
            chooseText.textSize = buttonFontSize * context.resources.displayMetrics.scaledDensity
        chooseText.typeface = defaultFont

        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(popup)
        bottomSheetDialog.show()

        chooseButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            if (displayView != null)
                (displayView as TextView).text = convert(CalendarAdapter.getSelected())
            selectedDate = CalendarAdapter.getSelected()
            todo()
        }

        if (gestureMonthSwitching) {
            val gestureDetector = GestureDetector(context, SwipeDetector(
                onSwipeLeft = {
                    switchMonth(
                        popup.findViewById<ImageView>(R.id.month_prev),
                        MotionDirection.TO_RIGHT.ordinal
                    )
                },
                onSwipeRight = {
                    switchMonth(
                        popup.findViewById<ImageView>(R.id.month_next),
                        MotionDirection.TO_LEFT.ordinal
                    )
                }
            ))

            popup.findViewById<GridView>(R.id.calendar).setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
        popup.findViewById<GridView>(R.id.calendarHead).setBackgroundColor(topBarBackgroundColor)
        if (daysBarVisibility == View.VISIBLE) {
            popup.findViewById<GridView>(R.id.calendarHead).adapter = object : BaseAdapter() {

                val days = listOf("S", "M", "T", "W", "T", "F", "S")

                override fun getCount(): Int = days.size

                override fun getItem(p0: Int): Any = days[p0]

                override fun getItemId(p0: Int): Long = p0.toLong()

                override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                    val view = p1 ?: LayoutInflater.from(context).inflate(R.layout.day, p2, false)
                    val dayText = view.findViewById<LinearLayout>(R.id.day_lly)
                        .findViewById<TextView>(R.id.dayText)
                    if (calendarFontSize > 0)
                        dayText.textSize = calendarFontSize
                    dayText.typeface = ResourcesCompat.getFont(context, font)
                    dayText.text = days[p0]
                    dayText.setBackgroundResource(topBarDaysBackground)
                    if (p0 == 0)
                        dayText.setTextColor(topBarSundayColor)
                    else
                        dayText.setTextColor(topBarDefaultDayColor)
                    return view
                }
            }
            popup.findViewById<GridView>(R.id.calendarHead).visibility = View.VISIBLE
        } else
            popup.findViewById<GridView>(R.id.calendarHead).visibility = View.GONE
        DateManager.setDate(context, dateL, popup, off, upperLimit, MotionDirection.TO_TOP.ordinal)
    }

    /**When user switches month*/
    private fun switchMonth(v: View, motionDirection: Int) {
        val currentDate: MutableList<Int> =
            DateManager.getDateArray(
                CalendarAdapter.getSelected()
            ).toMutableList()
        c.let { DateManager.adjustDate(it, currentDate, v, popup, upperLimit, motionDirection) }
    }

    /**When user switches year*/
    private fun yearDropdown(v: View) {
        val currentDate: MutableList<Int> = currentDate.toMutableList()
        val prevDate = selectedDate.split('-').map { Integer.valueOf(it) }
        c.let {
            DateManager.adjustDate(
                it,
                currentDate,
                v,
                popup,
                upperLimit,
                if (prevDate[2] < currentDate[2])
                    MotionDirection.TO_TOP.ordinal
                else
                    MotionDirection.TO_BOTTOM.ordinal
            )
        }
    }

    private fun convert(date: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("d-M-yyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return LocalDate.parse(date, inputFormatter).format(outputFormatter)
    }

    /**
     * Returns the date selected from the date picker
     */
    fun getSelectedDate() = selectedDate

}