package com.pepej.mobwars.service.impl

import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.UserService
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.adventure.platform.bukkit.BukkitAudiences
import com.pepej.papi.adventure.text.Component
import com.pepej.papi.adventure.text.Component.space
import com.pepej.papi.adventure.text.Component.text
import com.pepej.papi.adventure.text.format.NamedTextColor
import com.pepej.papi.events.Events
import com.pepej.papi.services.Services
import com.pepej.papi.terminable.TerminableConsumer
import com.pepej.papi.utils.Log
import com.pepej.papi.utils.Players
import com.pepej.papi.utils.entityspawner.EntitySpawner
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import kotlin.collections.HashSet

class UserServiceImpl : UserService {

    private val userCache by lazy { hashSetOf<User>() }
    private val bukkitAudiences by lazy { Services.load(BukkitAudiences::class.java) }

    init {
        loadAllOnlineUsers()
    }

    override fun getAllUsers(): Set<User> {
        return userCache
    }

    override fun setup(consumer: TerminableConsumer) {
        Events.subscribe(PlayerJoinEvent::class.java)
            .filter { getUserByPlayer(it.player) == null }
            .handler { registerUser(it.player.uniqueId, it.player.name) }
            .bindWith(consumer)
        Events.subscribe(PlayerQuitEvent::class.java)
            .filter { getUserByPlayer(it.player) != null }
            .handler { unregisterUser(it.player.uniqueId) }
            .bindWith(consumer)
    }

    override fun loadAllOnlineUsers() {
        Players.all().forEach { registerUser(it.uniqueId, it.name) }
    }

    override fun sendMessage(user: User?, message: String?) {
        val player = user?.asPlayer ?: return
        val audience = bukkitAudiences.player(player)
        val prefixComponent = text("[", NamedTextColor.AQUA)
            .append(text("M", NamedTextColor.YELLOW))
            .append(text("W", NamedTextColor.GREEN))
            .append(text("]", NamedTextColor.AQUA))
            .append(space())

        audience.sendMessage(prefixComponent.append(text(+message)))
    }

    override fun registerUser(id: UUID, username: String) {
        val user = User(id, username)
        Log.info("Loading user $user")
        userCache.add(user)
    }

    override fun unregisterUser(id: UUID) {
        val user = getUser(id)
        user?.currentArena?.leave(user)
        userCache.remove(user)
    }

    override fun getUser(id: UUID): User? {
        return userCache.find { it.id == id }
    }

    override fun getUserByPlayer(player: Player): User? = getUser(player.uniqueId)
}