package philosophicas.org.bitacora

import android.view.GestureDetector
import android.view.MotionEvent

class SwipeListener : GestureDetector.SimpleOnGestureListener() {

    val SWIPE_THRESHOLD = 100
    val SWIPE_VELOCITY_THRESHOLD = 100

    var onSwipeRight : ()->Unit = {}
    var onSwipeLeft : ()->Unit= {}


    override  fun onFling(e1: MotionEvent?, e2: MotionEvent?, vx: Float, vy: Float): Boolean {

        var result = false

        if(e1 != null && e2 != null) {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(vx) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                    result = true
                }
            }
        } else {
            result = false
        }

        return result

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

}