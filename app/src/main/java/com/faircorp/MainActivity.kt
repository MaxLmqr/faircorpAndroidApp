package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"
const val BUILDING_NAME_PARAM = "com.faircorp.buildingname.attribute"
const val BUILDING_ID_PARAM = "com.faircorp.buildingid.attribute"
const val ROOM_NAME_PARAM = "com.faircorp.roomname.attribute"
const val ROOM_CURRENT_TEMP_PARAM = "com.faircorp.roomct.attribute"
const val ROOM_TARGET_TEMP_PARAM = "com.faircorp.roomtt.attribute"
const val ROOM_ID_PARAM = "com.faircorp.roomid.attribute"


class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openBuildings(view: View) {
        startActivity(Intent(this,BuildingsActivity::class.java));
    }
}