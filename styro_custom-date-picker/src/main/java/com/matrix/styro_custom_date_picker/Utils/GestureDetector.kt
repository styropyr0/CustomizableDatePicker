package com.matrix.styro_custom_date_picker.Utils

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Custom gesture listener for detecting swipe gestures.
 * Author: Saurav Sajeev
 * Date: 2024-11-20
 */
internal class SwipeDetector(
    private val onSwipeLeft: () -> Unit,
    private val onSwipeRight: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 100f
    private val SWIPE_VELOCITY_THRESHOLD = 100f

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffX = e2.x - (e1?.x ?: 0f)
        val diffY = e2.y - (e1?.y ?: 0f)

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight()
                } else {
                    onSwipeLeft()
                }
                return true
            }
        }
        return false
    }
}
