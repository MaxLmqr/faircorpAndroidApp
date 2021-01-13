package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnRoomSelectedListener
import com.faircorp.model.RoomAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Loads rooms in a specific building. Loaded when user clicks on a building.
// Shows general informations and possibility to see windows or heaters in the specific room
// by clicking on buttons.
class RoomsActivity : BasicActivity(), OnRoomSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val name = intent.getStringExtra(BUILDING_NAME_PARAM)
        val id = intent.getLongExtra(BUILDING_ID_PARAM, 0)
        val recyclerView = findViewById<RecyclerView>(R.id.list_rooms)
        val adapter = RoomAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().buildingsApiService.findBuildingRooms(id).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            findViewById<TextView>(R.id.txt_building_name).text = name
                            adapter.update(it.body() ?: emptyList())
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on windows loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }

    override fun onRoomWindowsSelected(id: Long, name: String, current_temp: Double?, target_temp: Double?) {
        val intent = Intent(this, WindowsActivity::class.java)
        startActivity(intent.putExtra(ROOM_ID_PARAM, id))
    }

    override fun onRoomHeatersSelected(id: Long) {
        val intent = Intent(this, HeatersActivity::class.java)
        startActivity(intent.putExtra(ROOM_ID_PARAM, id))
    }
}