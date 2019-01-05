package philosophicas.org.bitacora

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import android.R.attr.autoText
import android.app.Activity
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import java.util.*


class EditFlightActivity : AppCompatActivity() {

    lateinit var callsign : AutoCompleteTextView
    lateinit var type : EditText
    lateinit var from : EditText
    lateinit var to : EditText
    lateinit var startHours : EditText
    lateinit var endHours : EditText
    lateinit var hours : EditText
    lateinit var swipeToDelete : Switch


    private lateinit var calendar : CalendarView
    private lateinit var saveFlightButton: Button



    private val fightTableName = "flights"
    private var lastROWID : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flight)


        callsign = findViewById(R.id.callsign)
        type = findViewById(R.id.type)
        from = findViewById(R.id.from)
        to = findViewById(R.id.to)
        startHours = findViewById(R.id.startHours)
        endHours = findViewById(R.id.endHours)
        hours = findViewById(R.id.hours)
        calendar = findViewById(R.id.calendarView)
        saveFlightButton = findViewById(R.id.save_flight_buttom)
        swipeToDelete = findViewById(R.id.swipe_to_delete_switch)


        val callsignsArray = loadCallsigns()


        if (callsignsArray.isNotEmpty()) {
            Log.w("XXX", callsignsArray.size.toString())
            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, callsignsArray)
            callsign.setAdapter(adapter)

        }


        this.lastROWID = intent.getLongExtra("lastROWID", -1)

        if (lastROWID > -1) {
            loadFlight(lastROWID)
            Log.i("Loading flight", "Loading flight number $lastROWID")
        }


        endHours.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) {
                updateHours()
            }
        }

        startHours.setOnFocusChangeListener{view, hasFocus ->
            if(!hasFocus) {
                updateHours()
            }
        }

        saveFlightButton.setOnClickListener { button -> saveFlight(button) }


        //Load parameter from last flight
        callsign.setOnItemClickListener { adapterView, view, i, l ->

            var cs =  adapterView.getItemAtPosition(i) as String

            print(cs)

            val bd = DataBaseHelper(this,fightTableName,null,1).writableDatabase
            val cursor = bd.rawQuery("select date, callsign, fromICAO,  toICAO,  aircraftType, startHours,  endHours  from flights where callsign = \"" + cs + "\" order by rowid desc limit 1",null)

            if (cursor != null && cursor.moveToFirst()) {
                this.type.text = stringToEditable(cursor.getString(4))
                this.startHours.text = stringToEditable(twoDecimalDoubleToString(cursor.getDouble(6)).replace(",","."))
                this.from.text = stringToEditable(cursor.getString(3))

            }

            cursor.close()
            bd.close()


        }

        swipeToDelete.setOnCheckedChangeListener{buttom, checked ->
            if (checked) {
                deleteFlight()
            }
        }


    }

    private fun updateHours () {
        hours.text =  stringToEditable(doubleDifferenceToString(endHours.text.toString(), startHours.text.toString()))
    }


    private fun deleteFlight() {

        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(R.string.ask_to_delete_flight)

        dialog.setPositiveButton(R.string.yes) { _, _ ->

            val bd = DataBaseHelper(this,fightTableName,null,1).writableDatabase
            bd.delete(fightTableName,"rowid=$lastROWID", null)
            bd.close()
            finish()
        }

        dialog.setNegativeButton(R.string.no,null)


        dialog.create().show()



    }



    private fun loadFlight(id: Long) {

        val bd = DataBaseHelper(this,fightTableName,null,1).writableDatabase
        val cursor = bd.rawQuery("select date, callsign,  fromICAO,  toICAO,  aircraftType, startHours,  endHours  from flights where rowid = $id", null)
        //date text, callsign text, fromICAO text, toICAO text, typeAircraft text, startHours real, endHours real
        cursor.moveToFirst()

        if (cursor!=null) {

            this.calendar.setDate(DateTools.toDate(cursor.getString(0)), true, true)

            //To show callsign on autoCompleteTextView
            this.callsign.postDelayed({ callsign.showDropDown() }, 500)
            this.callsign.setText(cursor.getString(1))
            this.callsign.setSelection(this.callsign.text.length)

            //Other
            this.from.setText(cursor.getString(2))
            this.to.setText(cursor.getString(3))
            this.type.setText(cursor.getString(4))
            this.startHours.setText(cursor.getDouble(5).toString())
            this.endHours.setText(cursor.getDouble(6).toString())
            updateHours()



        }



    }


    private fun loadCallsigns() : ArrayList<String>{

        val bd = DataBaseHelper(this,fightTableName,null,1).writableDatabase
        val cursor = bd.rawQuery("select distinct callsign from flights order by callsign", null)
        var result = ArrayList<String>()

        while(cursor.moveToNext()) result.add(cursor.getString(0))

        cursor.close()
        bd.close()

        return result

    }


    fun saveFlight(view: View) {

        val bd = DataBaseHelper(this, fightTableName, null, 1).writableDatabase
        val values = ContentValues()

        if(
            !callsign.text.isEmpty() &&
            !type.text.isEmpty() &&
            !from.text.isEmpty() &&
            !to.text.isEmpty() &&
            !startHours.text.isEmpty() &&
            !endHours.text.isEmpty()
        ) {

            values.put("date", DateTools.toString(calendar.date))
            values.put("callsign", callsign.text.toString())
            values.put("aircraftType", type.text.toString())
            values.put("fromICAO", from.text.toString())
            values.put("toICAO", to.text.toString())
            values.put("startHours", startHours.text.toString())
            values.put("endHours", endHours.text.toString())

            Log.d("Last row id", lastROWID.toString())
            bd.update(fightTableName,values,"ROWID=${this.lastROWID}",null)
            finish()


        } else {
            Toast.makeText(this, this.getString(R.string.field_empty_message), Toast.LENGTH_LONG).show()

        }

        bd.close()



    }






}
