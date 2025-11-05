package com.matrix.styro_custom_date_picker.DataHolders

/**
 * A set of values which determines the calendar adapter for datepicker
 * @property dates List of dates
 * @property firstDay First day of the month in terms of week days
 * @property lastDayOfMonth Last day of month in terms of week days
 * @property dateSet The current selected date
 * @property offset The offset to grey out dates after month's start to restrict past date selection
 * @property monthDays List of dates of the month
 * **/

internal data class CalendarSet(
    val dates: List<String>,
    val firstDay: Int,
    val lastDayOfMonth: Int,
    var dateSet: List<Int>,
    val offset: List<Int>,
    val monthDays: MutableList<List<Int>>
)