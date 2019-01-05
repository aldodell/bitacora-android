package philosophicas.org.bitacora

import org.json.JSONArray

class FlightArrayList : ArrayList<Flight>() {

    fun toJson() : String {
        var json = JSONArray()
        forEach { flight ->
            json.put(flight)
        }
        return json.toString(0)
    }

    companion object {
        fun createFrom(json: String) : FlightArrayList {
            var jsonArray = JSONArray(json)
            var flights = FlightArrayList()
            for (i in  0 .. (jsonArray.length() - 1)) {
                flights.add(jsonArray[i] as Flight)
            }
            return flights


        }
    }
}