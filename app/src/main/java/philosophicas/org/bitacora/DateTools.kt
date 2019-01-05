package philosophicas.org.bitacora

import android.widget.CalendarView
import java.text.SimpleDateFormat
import java.util.Calendar

class DateTools {
    companion object {

        fun toDate(s: String) : Long {
            val f = SimpleDateFormat("yyyy-MM-dd")
            val d = f.parse(s)
            return d.time
        }

        fun toString(date: Long) : String {
            val s = android.text.format.DateFormat.format("yyyy-MM-dd", date).toString()
            return s

        }

        fun now() : Long {
            return Calendar.getInstance().timeInMillis
        }

        fun nowToSting() : String {
            return toString(now())
        }



    }
}