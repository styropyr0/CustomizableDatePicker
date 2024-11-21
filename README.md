# Customizable Date Picker
#### Latest version: 1.0.2
This is a fully customizable date picker component for Android that offers a variety of customization options and a simple API for displaying a date picker with a wide range of features.

Please note that this library requires minSdk of 30.

## Features

- **Custom Date Highlighting**: Highlight specific dates with custom backgrounds or text colors.
- **Customizable UI**: Customize buttons, calendar layout, top bar, and date view appearance.
- **Flexible Date Range**: Set custom date ranges and enable/disable dates.
- **Simple to Use**: Easy-to-use API for showing the date picker and handling date selection.
- **Gesture Month Switching**: Option to enable swipe gestures for month navigation.

## Installation

To include the **Styro Custom Date Picker** in your project, add the following to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.matrix:styro_custom_date_picker:1.0.2'
}
```

or for gradle.kts

```gradle
dependencies {
    implementation("com.matrix:styro_custom_date_picker:1.0.2")
}
```

## Usage

### 1. Basic Setup and Usage

To show the date picker, use the `CustomizableDatePicker`'s `show()` method. You can customize the appearance of the date picker before calling this function by setting various parameters through the `CustomCalendarResources` singleton.

#### Code Example

```kotlin
package com.example.myapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.matrix.styro_custom_date_picker.CustomizableDatePicker
import com.matrix.styro_custom_date_picker.DataHolders.CustomCalendarResources
import com.matrix.styro_custom_date_picker.DateManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showDatePicker(v: View) {
        // Customize the date picker appearance
        CustomCalendarResources
            .setCalendarDaysHighlightTextColor(Color.RED) // Highlight selected date in red
            .setCalendarBackgroundColor(Color.WHITE) // Set calendar background color
            .setButtonDrawable(R.drawable.bg_button) // Set custom button background
            .setButtonTextColor(Color.WHITE) // Set button text color
            .setDaysBarVisibility(View.VISIBLE) // Show the days bar for month/year navigation

        // Create and show the date picker
        val datePicker = CustomizableDatePicker()
        datePicker.show(
            this, 
            "20-10-2023", // Default date
            v as Button, // Update the text of the button with the selected date (Optional)
            offset = "0-0-5", // Offset to show dates from 5 years in the past (Optional)
            upperLimit = "31-12-2025" // Limit date picker selection to 31st December 2025 (Optional)
        ) {
            // This block will be executed after the date is selected (optional)
            // You can add any extra functionality here
        }
        // Access the selected date
        val selectedDate = datePicker.getSelectedDate() // Returns the selected date as a string in dd-MM-yyyy format
        Log.d("Selected Date: $selectedDate") // Print or use the selected date
    }
}
```

### 2. Customization Options

You can customize the appearance and behavior of the `CustomizableDatePicker` using the `CustomCalendarResources` singleton. Below are the available customization options:

#### Calendar and Date Appearance

- **Highlight Date Background**:
  ```kotlin
  CustomCalendarResources.setCalendarDayHighlightBackground(R.drawable.highlight_background)
  ```

- **Default Date Background**:
  ```kotlin
  CustomCalendarResources.setCalendarDayBackgroundDefault(R.drawable.default_background)
  ```

- **Text Color for Calendar Days**:
  ```kotlin
  CustomCalendarResources.setCalendarDaysTextColor(Color.BLACK)
  ```

- **Text Color for Disabled Days**:
  ```kotlin
  CustomCalendarResources.setCalendarDaysDisabledTextColor(Color.GRAY)
  ```

- **Text Color for Highlighted Days**:
  ```kotlin
  CustomCalendarResources.setCalendarDaysHighlightTextColor(Color.RED)
  ```

- **Font**:
  ```kotlin
  CustomCalendarResources.setFont(R.font.dm_sans_medium)
  ```

#### Top Bar Customization

- **Top Bar Background Color**:
  ```kotlin
  CustomCalendarResources.setTopBarBackgroundColor(Color.WHITE)
  ```

- **Sunday Day Color in Top Bar**:
  ```kotlin
  CustomCalendarResources.setTopBarSundayColor(Color.RED)
  ```

- **Default Day Color in Top Bar**:
  ```kotlin
  CustomCalendarResources.setTopBarDefaultDayColor(Color.parseColor("#848487"))
  ```

#### Button Customization

- **Button Background**:
  ```kotlin
  CustomCalendarResources.setButtonDrawable(R.drawable.button_background)
  ```

- **Button Text Color**:
  ```kotlin
  CustomCalendarResources.setButtonTextColor(Color.WHITE)
  ```

#### Month & Year Navigation

- **Year Dropdown Background**:
  ```kotlin
  CustomCalendarResources.setYearDropDownBackground(R.drawable.year_dropdown_background)
  ```

- **Year Dropdown Text Color**:
  ```kotlin
  CustomCalendarResources.setYearDropdownTextColor(Color.parseColor("#474747"))
  ```

- **Month Switch Icons (Left and Right)**:
  ```kotlin
  CustomCalendarResources.setMonthSwitchIconLeft(R.drawable.ic_arrow_left)
  CustomCalendarResources.setMonthSwitchIconRight(R.drawable.ic_arrow_right)
  ```

- **Year Switch Icon**:
  ```kotlin
  CustomCalendarResources.setYearSwitchIcon(R.drawable.ic_arrow_down)
  ```

#### Popup and Gesture Support

- **Popup Background**:
  ```kotlin
  CustomCalendarResources.setPopupBackground(R.drawable.popup_background)
  ```

- **Enable Gesture-based Month Switching**:
  ```kotlin
  CustomCalendarResources.useGestureForSwitchingMonths(true)
  ```

### 3. The `show()` Method

The `show()` method is used to display the date picker with full flexibility. Here's a detailed breakdown of the parameters:

```kotlin
fun show(
    context: Context,
    date: String,
    displayView: View? = null, 
    offset: String = "0-0-0",
    upperLimit: String = today(), 
    todo: () -> Unit = {} 
)
```

#### Parameters:
- **`context`**: The `Context` in which the date picker will be displayed (usually `Activity` or `Fragment`).
- **`date`**: The date to show initially, in the format `dd-MM-yyyy`.
- **`displayView`**: A `TextView` or `Button` whose text will be updated with the selected date. If not provided, no view will be updated.
- **`offset`**: A string that defines the offset for date selection. The format is `"dd-mm-yyyy"`. For example, `"0-0-5"` will show dates from 5 years ago.
- **`upperLimit`**: The latest date a user can select, in the format `dd-MM-yyyy`. By default, this is today's date.
- **`todo`**: An optional callback that will be called after a date is selected. This allows you to perform actions after the date is picked.

#### Example:

```kotlin
val datePicker = CustomizableDatePicker()
datePicker.show(
    this,
    "20-10-2023", // Default date
    v as Button, // Button to display the selected date
    offset = "0-0-5", // Show dates from 5 years ago
    upperLimit = "31-12-2025" // Limit to dates up to 31st December 2025
) {
    // Code to run after date selection
}
```

### 4. DateManager Class

The `DateManager` class provides utility functions for managing and formatting dates.

#### Key Methods:

- **`today()`**: Returns today's date in `dd-MM-yyyy` format.
  
  ```kotlin
  val currentDate = DateManager.today() // "21-11-2024"
  ```

- **`formatDate(date: String, currentFormat: String, finalFormat: String)`**: Formats a given date from one format to another.

  ```kotlin
  val formattedDate = DateManager.formatDate("21-11-2024", "dd-MM-yyyy", "yyyy/MM/dd")
  ```

## Conclusion

The **Customizable Date Picker** provides a highly customizable and easy-to-use date picker for Android applications. With flexible options for appearance and behavior, you can tailor the date picker to match your app's design requirements. The integration of gesture-based month switching and an offset for date ranges makes it a versatile solution for various use cases.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
