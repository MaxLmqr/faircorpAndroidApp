package com.faircorp.model

enum class heaterStatus {ON, OFF}

class HeaterDto (val id: Long,
                 val heaterStatus: heaterStatus,
                 val name: String,
                 val roomId: Long,
                 val power: Long?) {

}