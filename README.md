# Customizable Date Picker

#### Latest version: 1.1
This is a fully customizable date picker component for Android that offers a variety of customization options and a simple API for displaying a date picker with a wide range of features.

Please note that this library requires Java Version 11 at the least, and  
```gradle
minSdk = 28  //or above
```

## Features

- **Custom Date Highlighting**: Highlight specific dates with custom backgrounds or text colors.  
- **Customizable UI**: Customize buttons, calendar layout, top bar, and date view appearance.  
- **Flexible Date Range**: Set custom date ranges and enable/disable dates.  
- **Simple to Use**: Easy-to-use API for showing the date picker and handling date selection.  
- **Gesture Month Switching**: Option to enable swipe gestures for month navigation.  

---

## Installation

To include the **Customizable Date Picker** in your project, follow the instructions below.

```gradle
dependencies {
    implementation 'com.github.styropyr0:CustomizableDatePicker:1.1'
}
```
or for `app:build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.styropyr0:CustomizableDatePicker:1.1")
}
```

Add the following to your `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
or for `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
```
---

## Customization Options

The `CustomCalendarResources` singleton provides a variety of methods for customizing the appearance and behavior of the date picker. Below is a comprehensive list of available options.

---

### **Date Picker Title**

- **Set Title Text**  
  Define the title text for the date picker.  
  ```kotlin
  CustomCalendarResources.setTitle("Select a Date")
  ```

- **Set Title Font Size**  
  Adjust the font size of the title text.  
  ```kotlin
  CustomCalendarResources.setTitleFontSize(16f)
  ```

- **Set Title Text Color**  
  Change the color of the title text.  
  ```kotlin
  CustomCalendarResources.setTitleColor(Color.BLACK)
  ```

- **Set Title Visibility**  
  Show or hide the title in the date picker.  
  ```kotlin
  CustomCalendarResources.setTitleVisibility(View.VISIBLE)
  ```

---

### **Drawer Customization**

- **Set Drawer Color**  
  Customize the color of the drawer.  
  ```kotlin
  CustomCalendarResources.setDrawerColor(Color.parseColor("#C3DBD3D2"))
  ```

- **Set Drawer Visibility**  
  Show or hide the drawer. 
  ```kotlin
  CustomCalendarResources.setDrawerVisibility(View.VISIBLE)
  ```

---

### **Top Bar Customization**

- **Top Bar Background Color**  
  Customize the background color of the top bar.  
  ```kotlin
  CustomCalendarResources.setTopBarBackgroundColor(Color.WHITE)
  ```

- **Sunday Day Color in Top Bar**  
  Define the color for Sundays in the top bar.  
  ```kotlin
  CustomCalendarResources.setTopBarSundayColor(Color.RED)
  ```

- **Default Day Color in Top Bar**  
  Specify the default color for other weekdays in the top bar.  
  ```kotlin
  CustomCalendarResources.setTopBarDefaultDayColor(Color.parseColor("#848487"))
  ```

- **Top Bar Days Background**  
  Set a background drawable for the top bar days.  
  ```kotlin
  CustomCalendarResources.setTopBarDaysBackground(R.drawable.days_background)
  ```

---

### **Calendar Customization**

- **Set Highlight Date Background**  
  Set the drawable for highlighting selected dates.  
  ```kotlin
  CustomCalendarResources.setCalendarDayHighlightBackground(R.drawable.highlight_background)
  ```

- **Set Default Date Background**  
  Define the default background for calendar dates.  
  ```kotlin
  CustomCalendarResources.setCalendarDayBackgroundDefault(R.drawable.default_background)
  ```

- **Set Calendar Days Text Color**  
  Change the text color for all calendar dates.  
  ```kotlin
  CustomCalendarResources.setCalendarDaysTextColor(Color.BLACK)
  ```

- **Set Disabled Days Text Color**  
  Customize the text color for disabled dates.  
  ```kotlin
  CustomCalendarResources.setCalendarDaysDisabledTextColor(Color.GRAY)
  ```

- **Set Highlighted Days Text Color**  
  Specify the text color for highlighted dates.  
  ```kotlin
  CustomCalendarResources.setCalendarDaysHighlightTextColor(Color.RED)
  ```

- **Set Calendar Background Color**  
  Adjust the background color of the calendar.  
  ```kotlin
  CustomCalendarResources.setCalendarBackgroundColor(Color.WHITE)
  ```

- **Set Font**  
  Change the font for all calendar text.  
  ```kotlin
  CustomCalendarResources.setFont(R.font.dm_sans_medium)
  ```

---

### **Buttons**

- **Button Background Drawable**  
  Set a custom drawable for the buttons.  
  ```kotlin
  CustomCalendarResources.setButtonDrawable(R.drawable.button_background)
  ```

- **Button Text Color**  
  Change the color of the button text.  
  ```kotlin
  CustomCalendarResources.setButtonTextColor(Color.WHITE)
  ```

- **Button Font Size**  
  Adjust the font size of the button text.  
  ```kotlin
  CustomCalendarResources.setButtonFontSize(14f)
  ```

---

### **Selectors**

- **Set Selector Background**  
  Define the background drawable for the selectors.  
  ```kotlin
  CustomCalendarResources.setSelectorBackground(R.drawable.selector_background)
  ```

- **Set Selector Text Color**  
  Change the text color for the selectors.  
  ```kotlin
  CustomCalendarResources.setSelectorTextColor(Color.BLACK)
  ```

- **Selector Font Size**  
  Adjust the font size for selectors (month/year).  
  ```kotlin
  CustomCalendarResources.setSelectorFontSize(16f)
  ```

---

### **Gestures and Navigation**

- **Enable Gesture-based Month Switching**  
  Toggle gestures for switching months.  
  ```kotlin
  CustomCalendarResources.useGestureForSwitchingMonths(true)
  ```

- **Month Switch Icons (Left and Right)**  
  Change the icons for navigating months.  
  ```kotlin
  CustomCalendarResources.setMonthSwitchIconLeft(R.drawable.ic_arrow_left)
  CustomCalendarResources.setMonthSwitchIconRight(R.drawable.ic_arrow_right)
  ```

- **Year Switch Icon**  
  Set the icon for switching years.  
  ```kotlin
  CustomCalendarResources.setYearSwitchIcon(R.drawable.ic_arrow_down)
  ```

---

### **Popup Customization**

- **Popup Background**  
  Customize the background of popups.  
  ```kotlin
  CustomCalendarResources.setPopupBackground(R.drawable.popup_background)
  ```

---

### **Font Sizes**

- **Calendar Font Size**  
  Adjust the font size for calendar text.  
  ```kotlin
  CustomCalendarResources.setCalendarFontSize(14f)
  ```

- **Year Dropdown Font Size**  
  Customize the font size for the year dropdown menu.  
  ```kotlin
  CustomCalendarResources.setYearDropdownFontSize(12f)
  ```
  
## The `show()` Method

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

## DateManager Class

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
