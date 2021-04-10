package com.pepej.mobwars.api

import com.pepej.mobwars.arena.ArenaConfig
import com.pepej.mobwars.model.User
import com.pepej.papi.scoreboard.Scoreboard
import com.pepej.papi.terminable.TerminableConsumer
import org.bukkit.World
import java.time.Duration

interface Arena : Runnable, TerminableConsumer {

    fun enable()

    fun disable()

    fun isActualToJoin(): Boolean

    fun start()

    fun stop()

    fun resetTimers()

    fun updateScoreboard()

    fun join(user: User, color: TeamColor = TeamColor.values().random())

    fun leave(user: User)

    fun startListening()

    fun context(): ArenaContext

    fun world(): World

    fun state(): ArenaState

    interface ArenaContext {

        fun config(): ArenaConfig

        fun users():  MutableMap<TeamColor, MutableSet<User>>

        fun scoreboard(): Scoreboard

    }

    enum class ArenaState(val state: String) {

        WAITING("Ожидание игроков"),
        DISABLED("Оффлайн"),
        STARTING("Игра начинается"),
        STOPPING("Перезагрузка"),
        STARTED("В игре");

    }

    enum class TeamColor(val color: String) {
        RED("&c"),
        BLUE("&b")
    }


}