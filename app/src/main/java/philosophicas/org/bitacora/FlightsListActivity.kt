package philosophicas.org.bitacora

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class FlightsListActivity : AppCompatActivity() {

    private val fightTableName = "flights"
    lateinit var listView : ListView
    lateinit var newFlight : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights_list)
        listView = findViewById<ListView>(R.id.flight_list)
        newFlight = findViewById<Button>(R.id.new_flight_button)
        newFlight.setOnClickListener {insertNewFlight()}


        listView.setOnItemClickListener { adapterView, view, i, l ->
            var lastROWID =  view.findViewById<TextView>(R.id.flight_list_row_id).text.toString().toLong()
            openEditFlightActivity(lastROWID)
        }


        loadFlights()




    }

    override fun onResume() {
        super.onResume()
        loadFlights()

    }

    private fun loadFlights() {

        var dbh = DataBaseHelper(this,"flights", null, 1)
        var db = dbh.writableDatabase
        var cursor = db.rawQuery("select date, callsign, aircraftType, fromICAO, toICAO, startHours, endHours, rowid from flights", null)
        var list : ArrayList<Flight> = ArrayList()

        Log.d("STARTING....", "NOW....")

        while (cursor.moveToNext()) {
            var flight = Flight(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5),
                cursor.getDouble(6),
                cursor.getLong(7)
            )
            list.add(flight)
            Log.d("Adding flight: ", flight.id.toString())

        }
        cursor.close()

        db.close()

        if (!list.isEmpty()) {
            // var  adapter =  ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list)
            var adapter = FlightListAdapter(this, list)
            listView.adapter = adapter
        }


    }



    private fun insertNewFlight() {

        Log.i("X", "Insertando vuelo")

        val bd = DataBaseHelper(this, fightTableName, null, 1).writableDatabase
        val values = ContentValues()
        values.put("date", DateTools.nowToSting())
        values.put("startHours", 0.0)
        values.put("endHours", 0.0)
        values.put("fromICAO", "")
        values.put("toICAO", "")
        values.put("aircraftType", "")
        values.put("callsign", "")


        var lastROWID =   bd.insert(fightTableName,"",values)
        Log.w("EditFlightActivity", "Inserting flight $lastROWID")
        bd.close()

        loadFlights()

        openEditFlightActivity(lastROWID)

    }

    fun openEditFlightActivity(lastROWID : Long) {

        val editFlightIntent = Intent(this, EditFlightActivity::class.java)
        editFlightIntent.putExtra("lastROWID", lastROWID)
        startActivity(editFlightIntent)

    }




}

