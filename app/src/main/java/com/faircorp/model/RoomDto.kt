package com.faircorp.model

class RoomDto(val id: Long,
              val name: String,
              val floor: Int,
              val current_temperature: Double?,
              val target_temperature: Double?) {

}