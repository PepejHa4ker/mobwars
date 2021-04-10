package com.pepej.mobwars.arena

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.UserService
import com.pepej.mobwars.service.impl.UserServiceImpl
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.scheduler.Schedulers
import com.pepej.papi.scoreboard.Scoreboard
import com.pepej.papi.services.Services
import com.pepej.papi.terminable.composite.CompositeTerminable
import com.pepej.papi.terminable.module.TerminableModule
import com.pepej.papi.utils.Log
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class SingleArena(private val world: World, private val context: Arena.ArenaContext) : Arena {

    private var state = Arena.ArenaState.DISABLED
    override fun updateScoreboard() {
        TODO("Not yet implemented")
    }

    private val compositeTerminable = CompositeTerminable.create()
    private val userService = Services.load(UserService::class.java)

    init {
        Schedulers.builder()
            .sync()
            .afterAndEvery(1)
            .run(this)
            .bindWith(compositeTerminable)

        Schedulers.sync()
            .runLater(::stop, 30, TimeUnit.SECONDS)
            .bindWith(this)

    }

    override fun enable() {
        Log.info("Enabling arena ${context.config().arenaId}")
        state = Arena.ArenaState.WAITING
    }

    override fun join(user: User, color: Arena.TeamColor) {
        context.users()[color]?.add(user)
        user.sendMessage("&eВы присоединились к${color.color} ${if (color == Arena.TeamColor.RED) "красной" else "синей"}&e команде")
        user.currentArena = this
        user.color = color
    }

    override fun resetTimers() {

    }

    override fun start() {
        Log.info("Starting arena ${context.config().arenaId}")
        state = Arena.ArenaState.STARTING


    }

    override fun startListening() {
        TODO("Not yet implemented")
    }

    override fun state(): Arena.ArenaState {
        return state
    }

    override fun stop() {
        Log.info("Stopping arena ${context.config().arenaId}")
        state = Arena.ArenaState.STOPPING
        context.users().flatMap { it.value }.forEach(::leave)

    }

    override fun world(): World {
        return world
    }


    override fun run() {

    }


    override fun disable() {
        Log.info("Disabling arena ${context.config().arenaId}")
        compositeTerminable.closeAndReportException()

    }

    override fun isActualToJoin(): Boolean {
        return true
    }

    override fun leave(user: User) {
        context.users()[user.color]?.remove(user)
        user.currentArena = null
        user.color = null
    }

    override fun context(): Arena.ArenaContext {
        return context
    }

    override fun <T : AutoCloseable> bind(t: T): T {
        return compositeTerminable.bind(t)
    }

    class SingleArenaContext(
        private val config: ArenaConfig,
        private val users: MutableMap<Arena.TeamColor, MutableSet<User>> = hashMapOf(Arena.TeamColor.BLUE to ConcurrentHashMap.newKeySet(), Arena.TeamColor.RED to ConcurrentHashMap.newKeySet()),
        private val scoreboard: Scoreboard
    ) : Arena.ArenaContext {

        override fun config(): ArenaConfig {
            return config
        }

        override fun users(): MutableMap<Arena.TeamColor, MutableSet<User>>{
            return users
        }

        override fun scoreboard(): Scoreboard {
            return scoreboard
        }

    }
}
