package com.pepej.mobwars.arena

import com.pepej.mobwars.SCOREBOARD_KEY
import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.UserService
import com.pepej.mobwars.service.impl.UserServiceImpl
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.metadata.Metadata
import com.pepej.papi.scheduler.Schedulers
import com.pepej.papi.scoreboard.Scoreboard
import com.pepej.papi.scoreboard.ScoreboardObjective
import com.pepej.papi.services.Services
import com.pepej.papi.terminable.composite.CompositeTerminable
import com.pepej.papi.terminable.module.TerminableModule
import com.pepej.papi.utils.Log
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import org.bukkit.scoreboard.DisplaySlot
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class SingleArena(private val world: World, private val context: Arena.ArenaContext) : Arena {

    private var state = Arena.ArenaState.DISABLED

    override fun updateScoreboard(user: User?, objective: ScoreboardObjective?) {
        objective?.apply {
            val team = if (user?.color == Arena.TeamColor.RED) "красных" else "синих"
            applyLines(
                "&0",
                "  &aАрена: &7${context.config().arenaName}",
                "&1",
                "  &aВы играете за ${user?.color?.color + team}",
                "&2",
                "  &cКрасные&7: &e${context.users()[Arena.TeamColor.RED]?.size}/5",
                "  &bСиние&7: &e${context.users()[Arena.TeamColor.BLUE]?.size}/5",
            )
        }
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

    override fun broadcastMessage(message: String?, onlyForSameTeam: Boolean, team: Arena.TeamColor?) {
        if (onlyForSameTeam) {
            context.users()[team ?: return]?.forEach {
                it?.sendMessage(message)
            }
        } else {
            context.users().flatMap { it.value }.forEach {
                it?.sendMessage(message)
            }
        }
    }

    override fun enable() {
        Log.info("Enabling arena ${context.config().arenaId}")
        state = Arena.ArenaState.WAITING
    }

    override fun join(user: User?, color: Arena.TeamColor) {
        if (user?.currentArena != null) {
            return
        }
        context.users()[color]?.add(user ?: return)
        user?.sendMessage("&eВы присоединились к ${color.color + getTeamName(color)}&e команде")
        broadcastMessage("&a${user?.username}&e присоединился к ${color.color + getTeamName(color)}&e команде ")
        user?.currentArena = this
        user?.color = color
        val objective =
            context.scoreboard().createPlayerObjective(user?.asPlayer, "&eMob&bWars", DisplaySlot.SIDEBAR)
        Metadata.provideForPlayer(user?.asPlayer ?: return).put(SCOREBOARD_KEY, objective)
    }

    private fun getTeamName(color: Arena.TeamColor) =
        if (color == Arena.TeamColor.RED) "красной" else "синей"

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
        state = Arena.ArenaState.WAITING

    }

    override fun world(): World {
        return world
    }


    override fun run() {
        Metadata.lookupPlayersWithKey(SCOREBOARD_KEY).forEach { (player, objective) ->
            val user = userService.getUserByPlayer(player)
            updateScoreboard(user, objective)
        }
    }


    override fun disable() {
        Log.info("Disabling arena ${context.config().arenaId}")
        compositeTerminable.closeAndReportException()

    }

    override fun isActualToJoin(): Boolean {
        return true
    }

    override fun leave(user: User?) {
        context.users().flatMap { it.value }.toMutableSet().remove(user)
        broadcastMessage("&7${user?.username}&e вышел")
        user?.currentArena = null
        user?.color = null
        val objective = Metadata.provideForPlayer(user?.asPlayer ?: return)[SCOREBOARD_KEY]
        objective.ifPresent {
            it.unsubscribe(user.asPlayer)
        }

    }

    override fun context(): Arena.ArenaContext {
        return context
    }

    override fun <T : AutoCloseable> bind(t: T): T {
        return compositeTerminable.bind(t)
    }

    class SingleArenaContext(
        private val config: ArenaConfig,
        private val users: MutableMap<Arena.TeamColor, MutableSet<User?>> = hashMapOf(
            Arena.TeamColor.BLUE to ConcurrentHashMap.newKeySet(),
            Arena.TeamColor.RED to ConcurrentHashMap.newKeySet()
        ),
        private val scoreboard: Scoreboard
    ) : Arena.ArenaContext {

        override fun config(): ArenaConfig {
            return config
        }

        override fun users(): MutableMap<Arena.TeamColor, MutableSet<User?>> {
            return users
        }

              override fun scoreboard(): Scoreboard {
            return scoreboard
        }

    }
}
