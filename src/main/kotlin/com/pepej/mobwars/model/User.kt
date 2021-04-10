package com.pepej.mobwars.model

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.service.UserService
import com.pepej.papi.services.Services
import com.pepej.papi.utils.Players
import org.bukkit.entity.Player
import java.util.*

data class User(
    val id: UUID,
    val username: String,
    var exp: Int = 0,
    var currentArena: Arena? = null,
    var color: Arena.TeamColor? = null
) {

    private val userService by lazy { Services.load(UserService::class.java) }

    val asPlayer: Player?
    get() {
        return Players.getNullable(id)
    }

    fun sendMessage(message: String?) {
        userService.sendMessage(this, message)
    }
}
