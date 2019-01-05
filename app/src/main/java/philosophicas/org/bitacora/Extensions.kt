package philosophicas.org.bitacora

import android.text.Editable


public inline fun twoDecimalDoubleToString(d : Double) : String {
    var s = "%.2f".format(d)
    return s
}

public inline fun doubleDifferenceToString(a: String, b: String, digits : Int = 2, notLessZero : Boolean = true) : String {
    var d1 = a.toDoubleOrNull() ?: 0.0
    var d2 = b.toDoubleOrNull() ?: 0.0
    var r = d1 - d2

    if (notLessZero && r < 0) r = 0.0

    var s = "%.${digits}f".format(r)

    return s
}

public inline  fun stringToEditable(s: String) : Editable {
    return Editable.Factory.getInstance().newEditable(s)
}