package philosophicas.org.bitacora

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast


class FlightListViewHolder {

    lateinit var callsign : TextView
    lateinit var aircraftType : TextView
    lateinit var fromICAO : TextView
    lateinit var toICAO : TextView
    lateinit var date : TextView
    lateinit var startHours : TextView
    lateinit var endHours : TextView
    lateinit var hours : TextView
    lateinit var rowid : TextView



}

class FlightListAdapter (
    private val context : Context,
    private val dataSource : ArrayList<Flight>
) : BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var holder  : FlightListViewHolder





    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {

        var flight = getItem(pos) as Flight
        var view : View

        if (convertView==null) {

            view = inflater.inflate(R.layout.flight_list_row, parent, false)
            holder = FlightListViewHolder()
            holder.callsign = view.findViewById(R.id.flight_list_row_callsign) as TextView
            holder.aircraftType = view.findViewById(R.id.flight_list_row_aircraft_type) as TextView
            holder.fromICAO = view.findViewById(R.id.flight_list_row_from)  as TextView
            holder.toICAO = view.findViewById(R.id.flight_list_row_to)  as TextView
            holder.date = view.findViewById(R.id.flight_list_row_date)  as TextView
            holder.startHours = view.findViewById(R.id.flight_list_row_start_hours)
            holder.endHours = view.findViewById(R.id.flight_list_row_end_hours)
            holder.hours = view.findViewById(R.id.flight_list_row_hours)
            holder.rowid = view.findViewById(R.id.flight_list_row_id)


            view.tag = holder


            /*
            var swipeListener = SwipeListener()
            swipeListener.onSwipeRight = {
                //  Toast.makeText(view.context, holder.rowid.text.toString(),Toast.LENGTH_LONG).show()
            }

            var gestureDetector = GestureDetector(view.context, swipeListener)
            view.setOnTouchListener { _, motionEvent ->
                gestureDetector.onTouchEvent(motionEvent)
            }
            */

        } else {
            view = convertView
            holder = convertView.tag as FlightListViewHolder
        }

        holder.callsign.setText(flight.callsign)
        holder.aircraftType.setText(flight.aircraftType)
        holder.fromICAO.setText(flight.fromICAO)
        holder.toICAO.setText(flight.toICAO)
        holder.date.setText(flight.date)
        holder.startHours.setText(flight.startHours.toString())
        holder.endHours.setText(flight.endHours.toString())
        holder.hours.setText((twoDecimalDoubleToString(flight.endHours - flight.startHours)).toString())
        holder.rowid.setText(flight.id.toString())


        return view


    }

    override fun getItem(pos: Int): Any {
        return dataSource[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }




}