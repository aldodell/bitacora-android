package philosophicas.org.bitacora

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


public inline fun String.toDouble() : Double = java.lang.Double.parseDouble(this)


class DataBaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(
        context,
        name,
        factory,
        version
    ) {
    override fun onCreate(bd: SQLiteDatabase?) {
        //date, callsign,  fromICAO,  toICAO,  typeAircraft, startHours,  endHours
        bd!!.execSQL("create table flights (date text, callsign text, aircraftType text, fromICAO text, toICAO text, startHours real, endHours real)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


//