package com.faircorp.model

interface OnRoomSelectedListener {
    fun onRoomWindowsSelected(id: Long, name: String, current_temp: Double?, target_temp: Double?)
    fun onRoomHeatersSelected(id: Long)
}