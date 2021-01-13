package com.faircorp.model

enum class windowStatus { OPEN, CLOSED }

class WindowDto(val id: Long,
                val name: String,
                val roomName: String,
                val windowStatus: windowStatus,
                val roomId: Long)