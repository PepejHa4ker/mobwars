package com.pepej.mobwars.service

import com.pepej.mobwars.model.User
import com.pepej.papi.terminable.module.TerminableModule
import org.bukkit.entity.Player
import java.util.*


interface UserService : TerminableModule {

    fun loadAllOnlineUsers();

    fun sendMessage(user: User?, message: String?)

    fun registerUser(id: UUID, username: String)

    fun unregisterUser(id: UUID)

    fun getUser(id: UUID): User?

    fun getUserByPlayer(player: Player): User?

    fun getAllUsers(): Set<User>


}