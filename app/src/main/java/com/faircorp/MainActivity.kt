package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View

const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"
const val BUILDING_NAME_PARAM = "com.faircorp.buildingname.attribute"
const val BUILDING_ID_PARAM = "com.faircorp.buildingid.attribute"
const val ROOM_ID_PARAM = "com.faircorp.roomid.attribute"

// Welcome page.
class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openBuildings(view: View) {
        startActivity(Intent(this, BuildingsActivity::class.java))
    }
}