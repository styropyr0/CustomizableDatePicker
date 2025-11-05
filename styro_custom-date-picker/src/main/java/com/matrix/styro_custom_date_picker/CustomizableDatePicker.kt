package com.matrix.styro_custom_date_picker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
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
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonDrawable
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.buttonTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.calendarFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitle
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.datePickerTitleVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.daysBarVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.drawerVisibility
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.monthSwitchIconLeft
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.monthSwitchIconRight
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.popupBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorTextColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarBackgroundColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.gestureMonthSwitching
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.selectorFontSize
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarDaysBackground
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarDefaultDayColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.topBarSundayColor
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources.yearSwitchIcon
import com.matrix.styro_custom_date_picker.Enums.MotionDirection
import com.matrix.styro_custom_date_picker.Utils.SwipeDetector
import com.matrix.styro_custom_date_picker.callbacks.OnDateSelectListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
     * @param weekSelection True to show the date picker in week selection mode.
     * @param dateSelectListener A listener to execute callback upon date selection.
     * **/
    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    fun show(
        context: Context,
        date: String,
        displayView: View? = null,
        offset: String = "0-0-0",
        upperLimit: String = today(),
        weekSelection: Boolean = false,
        dateSelectListener: OnDateSelectListener,
    ) {
        this.upperLimit = upperLimit
        c = context

        val layoutInflater = LayoutInflater.from(context)
        val defaultFont = ResourcesCompat.getFont(context, CustomCalendarResources.font)

        popup = layoutInflater.inflate(R.layout.calendar, null).apply {
            findViewById<LinearLayout>(R.id.monthSwitcher).setBackgroundResource(selectorBackground)
            findViewById<LinearLayout>(R.id.year).setBackgroundResource(selectorBackground)

            CustomCalendarResources.weekSelection = weekSelection

            listOf<TextView>(findViewById(R.id.month), findViewById(R.id.year_text)).apply {
                forEach {
                    it.setTextColor(selectorTextColor)
                    it.setTypeface(defaultFont, Typeface.BOLD)
                    if (selectorFontSize > 0) it.textSize = selectorFontSize
                }
            }

            findViewById<ImageView>(R.id.month_next).setImageResource(monthSwitchIconRight)
            findViewById<ImageView>(R.id.month_prev).setImageResource(monthSwitchIconLeft)
            findViewById<ImageView>(R.id.year_icon).setImageResource(yearSwitchIcon)

            findViewById<TextView>(R.id.title).apply {
                if (datePickerTitleVisibility == View.VISIBLE && datePickerTitle.isNotEmpty()) {
                    typeface = defaultFont
                    text = datePickerTitle
                    visibility = datePickerTitleVisibility
                    setTextColor(datePickerTitleColor)
                    if (datePickerTitleFontSize > 0) textSize = datePickerTitleFontSize
                } else {
                    visibility = View.GONE
                }
            }

            findViewById<ImageView>(R.id.drawer).apply {
                visibility = drawerVisibility
            }
        }

        var dateL = DateManager.getDateArray(date)
        val upperDateL = DateManager.getDateArray(upperLimit)
        if (dateL[2] > upperDateL[2] ||
            (dateL[2] == upperDateL[2] && (dateL[1] > upperDateL[1] ||
                    (dateL[1] == upperDateL[1] && dateL[0] > upperDateL[0])))
        ) dateL = upperDateL

        val off = DateManager.getDateArray(offset)

        with(popup) {
            findViewById<ImageView>(R.id.month_next).setOnClickListener {
                switchMonth(it, MotionDirection.TO_LEFT.ordinal)
            }
            findViewById<ImageView>(R.id.month_prev).setOnClickListener {
                switchMonth(it, MotionDirection.TO_RIGHT.ordinal)
            }
            findViewById<LinearLayout>(R.id.year).setOnClickListener {
                yearDropdown(it)
            }
        }

        val chooseButton = popup.findViewById<LinearLayout>(R.id.calendar_choose)
        val chooseText = popup.findViewById<TextView>(R.id.choose_text)

        chooseButton.apply {
            setBackgroundResource(buttonDrawable)
            chooseText.apply {
                setTextColor(buttonTextColor)
                setTypeface(defaultFont, Typeface.BOLD)
                if (buttonFontSize > 0)
                    textSize = buttonFontSize * context.resources.displayMetrics.scaledDensity
            }
        }

        val bottomSheetDialog = BottomSheetDialog(context).apply {
            setContentView(popup)
            setOnShowListener {
                val bottomSheet = (it as BottomSheetDialog)
                    .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(popupBackground)
            }
            show()
        }

        chooseButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            (displayView as? TextView)?.text = convert(CalendarAdapter.getSelected())
            selectedDate = CalendarAdapter.getSelected()
            if (weekSelection)
                dateSelectListener.onDateSelected(getSelectedWeek())
            else
                dateSelectListener.onDateSelected(getSelectedDate())
        }

        if (gestureMonthSwitching) {
            val gestureDetector = GestureDetector(
                context,
                SwipeDetector(
                    onSwipeLeft = {
                        switchMonth(
                            popup.findViewById(R.id.month_prev),
                            MotionDirection.TO_RIGHT.ordinal
                        )
                    },
                    onSwipeRight = {
                        switchMonth(
                            popup.findViewById(R.id.month_next),
                            MotionDirection.TO_LEFT.ordinal
                        )
                    }
                )
            )

            popup.findViewById<GridView>(R.id.calendar).apply {
                isClickable = true
                isFocusable = true
                isFocusableInTouchMode = true
                parent?.requestDisallowInterceptTouchEvent(true)
                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    true
                }
            }
        }

        popup.findViewById<GridView>(R.id.calendarHead).apply {
            setBackgroundColor(topBarBackgroundColor)
            visibility = daysBarVisibility
            if (daysBarVisibility == View.VISIBLE) {
                adapter = object : BaseAdapter() {
                    val days = listOf("M", "T", "W", "T", "F", "S", "S")
                    override fun getCount() = days.size
                    override fun getItem(p0: Int) = days[p0]
                    override fun getItemId(p0: Int) = p0.toLong()
                    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                        val view = p1 ?: LayoutInflater.from(context).inflate(R.layout.day, p2, false)
                        val dayText = view.findViewById<TextView>(R.id.dayText)
                        dayText.apply {
                            text = days[p0]
                            setTypeface(defaultFont, Typeface.BOLD)
                            if (calendarFontSize > 0) textSize = calendarFontSize
                            setBackgroundResource(topBarDaysBackground)
                            setTextColor(if (p0 == 6) topBarSundayColor else topBarDefaultDayColor)
                        }
                        return view
                    }
                }
            }
        }

        DateManager.setDate(context, dateL, popup, off, upperLimit, MotionDirection.TO_TOP.ordinal)
    }

    /** Handles month switching */
    private fun switchMonth(v: View, motionDirection: Int) {
        val currentDate = DateManager.getDateArray(CalendarAdapter.getSelected()).toMutableList()
        c.let { DateManager.adjustDate(it, currentDate, v, popup, upperLimit, motionDirection) }
    }

    /** Handles year dropdown */
    private fun yearDropdown(v: View) {
        val prevDate = selectedDate.split('-').map { it.toInt() }
        val currentDate = currentDate.toMutableList()
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
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.getDefault())
            val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
            LocalDate.parse(date, inputFormatter).format(outputFormatter)
        } catch (_: Exception) {
            date
        }
    }

    fun getSelectedDate() = selectedDate
    fun getSelectedWeek() = CalendarAdapter.getSelectedWeek()
}