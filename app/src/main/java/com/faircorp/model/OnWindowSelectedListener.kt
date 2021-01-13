package com.faircorp.model

interface OnWindowSelectedListener {
    fun onWindowSelected(id: Long)
    fun onWindowSwitchButtonSelected(id: Long, adapter: WindowAdapter, roomId: Long)
}