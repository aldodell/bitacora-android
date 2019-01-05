package philosophicas.org.bitacora

import org.json.JSONObject


class Flight (

    var date: String,
    var callsign: String,
    var aircraftType: String,
    var fromICAO : String,
    var toICAO :  String,
    var startHours : Double,
    var endHours: Double,
    var id: Long
) {




    fun toJSON() : String {

        var jsonObject : JSONObject = JSONObject()

        jsonObject.put("date", date)
        jsonObject.put("callsign", callsign)
        jsonObject.put("aircraftType", aircraftType)
        jsonObject.put("fromICAO", fromICAO)
        jsonObject.put("toICAO", toICAO)
        jsonObject.put("startHours", startHours)
        jsonObject.put("endHours", endHours)
        jsonObject.put("id", id)

        return jsonObject.toString(0)

    }



    companion object {

        fun createFrom(json : String) : Flight {
            var jsonObject = JSONObject(json)

            var f = Flight(
                jsonObject.getString("date"),
                jsonObject.getString("callsign"),
                jsonObject.getString("aircraftType"),
                jsonObject.getString("fromICAO"),
                jsonObject.getString("toICAO"),
                jsonObject.getDouble("startHours"),
                jsonObject.getDouble("endHours"),
                jsonObject.getLong("id")
            )

            return f
        }

    }







    }

