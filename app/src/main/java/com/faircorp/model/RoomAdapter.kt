package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R


// Adapter to fill the room recycler.
class RoomAdapter(val listener: OnRoomSelectedListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items = mutableListOf<RoomDto>()


    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_room_name)
        val current_temperature: TextView = view.findViewById(R.id.txt_current_temperature)
        val target_temperature: TextView = view.findViewById(R.id.txt_target_temperature)
        val floor: TextView = view.findViewById(R.id.txt_floor_number)
        val w: Button = view.findViewById(R.id.windows_button)
        val h: Button = view.findViewById(R.id.heaters_button)

    }

    fun update(rooms: List<RoomDto>) {  // (4)
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = items[position]
        holder.apply {
            name.text = room.name
            current_temperature.text = room.current_temperature.toString()
            target_temperature.text = room.target_temperature.toString()
            floor.text = room.floor.toString()
            w.setOnClickListener { listener.onRoomWindowsSelected(room.id, room.name, room.current_temperature, room.target_temperature) }
            h.setOnClickListener { listener.onRoomHeatersSelected(room.id) }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}