package com.matrix.styro_custom_date_picker.Utils

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

/**
 * Custom gesture listener for detecting swipe gestures.
 * Author: Saurav Sajeev
 * Date: 2024-11-20
 */

internal class SwipeDetector(
    private val onSwipeLeft: () -> Unit,
    private val onSwipeRight: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 30f
    private val SWIPE_VELOCITY_THRESHOLD = 30f

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 != null) {
            val diffX = e2.x - e1.x
            val diffY = e2.y - e1.y

            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeLeft()
                    } else {
                        onSwipeRight()
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }
}
