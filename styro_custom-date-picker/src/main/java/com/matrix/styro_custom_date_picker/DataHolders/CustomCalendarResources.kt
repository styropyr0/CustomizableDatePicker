package com.matrix.styro_custom_date_picker.DataHolders

import android.graphics.Color
import android.view.View
import com.matrix.styro_custom_date_picker.R

/**
 * Change the appearance of the date picker by setting values into the respective parameters.
 *
 * Author: Saurav Sajeev
 * Date: 2024-11-20
 */

object CustomCalendarResources {
    internal var dayHighlightBackground: Int = R.drawable.calendar_highlight
    internal var dayBackgroundDefault: Int = R.drawable.calendar_click
    internal var yearDropDownBackground: Int = R.drawable.warning_box_bg
    internal var yearDropdownTextColor: Int = Color.parseColor("#474747")
    internal var selectorBackground: Int = R.drawable.calendar_highlight
    internal var selectorTextColor: Int = Color.parseColor("#474747")
    internal var font: Int = R.font.dm_sans_medium
    internal var calendarBackgroundColor: Int = Color.TRANSPARENT
    internal var topBarBackgroundColor: Int = Color.TRANSPARENT
    internal var buttonDrawable: Int = R.drawable.button_bg
    internal var buttonTextColor: Int = Color.WHITE
    internal var daysBarVisibility = View.VISIBLE
    internal var topBarSundayColor = Color.parseColor("#EC3925")
    internal var topBarDefaultDayColor = Color.parseColor("#848487")
    internal var topBarDaysBackground = R.drawable.top_bar_bg
    internal var calendarDaysTextColor = Color.parseColor("#474747")
    internal var calendarDaysDisabledTextColor: Int = Color.parseColor("#C0C0C2")
    internal var calendarDaysHighlightTextColor = Color.parseColor("#EC3925")
    internal var popupBackground = R.drawable.popup_bg
    internal var monthSwitchIconLeft = R.drawable.ic_arrow_left
    internal var monthSwitchIconRight = R.drawable.ic_arrow_right
    internal var yearSwitchIcon = R.drawable.ic_arrow_down
    internal var gestureMonthSwitching = true
    internal var calendarFontSize: Float = 0f
    internal var yearDropdownfontSize: Float = 0f
    internal var selectorFontSize: Float = 0f
    internal var buttonFontSize: Float = 0f
    internal var datePickerTitleFontSize: Float = 0f
    internal var datePickerTitle: String = ""
    internal var datePickerTitleVisibility: Int = View.VISIBLE
    internal var datePickerTitleColor = Color.BLACK
    internal var drawerColor = Color.parseColor("#C3DBD3D2")
    internal var drawerVisibility = View.VISIBLE

    fun setCalendarDayHighlightBackground(backgroundDrawable: Int): CustomCalendarResources {
        dayHighlightBackground = backgroundDrawable
        return this
    }

    fun setCalendarDayBackgroundDefault(backgroundDrawable: Int): CustomCalendarResources {
        dayBackgroundDefault = backgroundDrawable
        return this
    }

    fun setYearDropDownBackground(backgroundDrawable: Int): CustomCalendarResources {
        yearDropDownBackground = backgroundDrawable
        return this
    }

    fun setYearDropdownTextColor(color: Int): CustomCalendarResources {
        yearDropdownTextColor = color
        return this
    }

    fun setSelectorBackground(backgroundDrawable: Int): CustomCalendarResources {
        selectorBackground = backgroundDrawable
        return this
    }

    fun setSelectorTextColor(color: Int): CustomCalendarResources {
        selectorTextColor = color
        return this
    }

    fun setFont(font: Int): CustomCalendarResources {
        this.font = font
        return this
    }

    fun setCalendarBackgroundColor(color: Int): CustomCalendarResources {
        calendarBackgroundColor = color
        return this
    }

    fun setTopBarBackgroundColor(color: Int): CustomCalendarResources {
        topBarBackgroundColor = color
        return this
    }

    fun setButtonDrawable(drawable: Int): CustomCalendarResources {
        buttonDrawable = drawable
        return this
    }

    fun setButtonTextColor(color: Int): CustomCalendarResources {
        buttonTextColor = color
        return this
    }

    fun setDaysBarVisibility(visibility: Int): CustomCalendarResources {
        daysBarVisibility = visibility
        return this
    }

    fun setTopBarSundayColor(color: Int): CustomCalendarResources {
        topBarSundayColor = color
        return this
    }

    fun setTopBarDefaultDayColor(color: Int): CustomCalendarResources {
        topBarDefaultDayColor = color
        return this
    }

    fun setTopBarDaysBackground(drawable: Int): CustomCalendarResources {
        topBarDaysBackground = drawable
        return this
    }

    fun setCalendarDaysTextColor(color: Int): CustomCalendarResources {
        calendarDaysTextColor = color
        return this
    }

    fun setCalendarDaysDisabledTextColor(color: Int): CustomCalendarResources {
        calendarDaysDisabledTextColor = color
        return this
    }

    fun setCalendarDaysHighlightTextColor(color: Int): CustomCalendarResources {
        calendarDaysHighlightTextColor = color
        return this
    }

    fun setPopupBackground(drawable: Int): CustomCalendarResources {
        popupBackground = drawable
        return this
    }

    fun setMonthSwitchIconLeft(drawable: Int): CustomCalendarResources {
        monthSwitchIconLeft = drawable
        return this
    }

    fun setMonthSwitchIconRight(drawable: Int): CustomCalendarResources {
        monthSwitchIconRight = drawable
        return this
    }

    fun setYearSwitchIcon(drawable: Int): CustomCalendarResources {
        yearSwitchIcon = drawable
        return this
    }

    fun useGestureForSwitchingMonths(state: Boolean): CustomCalendarResources {
        gestureMonthSwitching = state
        return this
    }

    fun setCalendarFontSize(size: Float): CustomCalendarResources {
        calendarFontSize = size
        return this
    }

    fun setYearDropdownFontSize(size: Float): CustomCalendarResources {
        yearDropdownfontSize = size
        return this
    }

    fun setSelectorFontSize(size: Float): CustomCalendarResources {
        selectorFontSize = size
        return this
    }

    fun setButtonFontSize(size: Float): CustomCalendarResources {
        buttonFontSize = size
        return this
    }

    fun setTitleFontSize(size: Float): CustomCalendarResources {
        datePickerTitleFontSize = size
        return this
    }

    fun setTitle(title: String): CustomCalendarResources {
        datePickerTitle = if(title.trim().isNotEmpty()) title else ""
        return this
    }

    fun setTitleVisibility(visibility: Int): CustomCalendarResources {
        datePickerTitleVisibility = visibility
        return this
    }

    fun setTitleColor(color: Int): CustomCalendarResources {
        datePickerTitleColor = color
        return this
    }

    fun setDrawerColor(color: Int): CustomCalendarResources {
        drawerColor = color
        return this
    }

    fun setDrawerVisibility(visibility: Int): CustomCalendarResources {
        drawerVisibility = visibility
        return this
    }

}