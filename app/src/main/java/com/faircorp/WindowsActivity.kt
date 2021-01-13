package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.WindowAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Show windows in a specific rooms. Possibility to switch status of all windows, or a particular
// windows. In order to update the display when a status has been changed, I'm calling one more time
// the api to get all the modifications and then update the recycler.
class WindowsActivity : BasicActivity(), OnWindowSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows)

        val recyclerView = findViewById<RecyclerView>(R.id.list_windows) // (2)
        val switchWindowsButton = findViewById<Button>(R.id.switch_button)
        val adapter = WindowAdapter(this) // (3)
        val id = intent.getLongExtra(ROOM_ID_PARAM, 0)

        switchWindowsButton.setOnClickListener { onSwitchButton(id, adapter) }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        updateRecycler(adapter, id)
    }


    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }

    private fun updateRecycler(adapter: WindowAdapter, id: Long) {
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomApiService.findRoomWindows(id).execute() } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
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

    override fun onWindowSwitchButtonSelected(id: Long, adapter: WindowAdapter, roomId: Long) {
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.switchById(id).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            updateRecycler(adapter, roomId)
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on windows status switch $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }

    private fun onSwitchButton(id: Long, adapter: WindowAdapter) {
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().roomApiService.switchRoomWindows(id).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            updateRecycler(adapter, id)
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on windows status switch $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }

        }
    }
}