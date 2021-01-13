package com.faircorp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import com.faircorp.model.RoomDto
import com.faircorp.model.WindowDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var roomInfo: RoomDto
        var windowInfo: WindowDto
        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute() } // (2)
                    .onSuccess {
                        windowInfo = it.body()!!
                        runCatching { ApiServices().roomApiService.findById(windowInfo.roomId).execute() } // (2)
                                .onSuccess {
                                    roomInfo = it.body()!!
                                    withContext(context = Dispatchers.Main) {
                                        findViewById<TextView>(R.id.txt_window_name).text = windowInfo.name
                                        findViewById<TextView>(R.id.txt_room_name).text = windowInfo.roomName
                                        findViewById<TextView>(R.id.txt_window_current_temperature).text =
                                                if (roomInfo.current_temperature == null) "undefined" else roomInfo.current_temperature.toString()
                                        findViewById<TextView>(R.id.txt_window_target_temperature).text =
                                                if (roomInfo.target_temperature == null) "undefined" else roomInfo.target_temperature.toString()
                                        findViewById<TextView>(R.id.txt_window_status).text =
                                                windowInfo.windowStatus.toString()
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
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                    applicationContext,
                                    "Error on rooms loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }
}