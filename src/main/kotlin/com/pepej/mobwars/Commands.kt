package com.pepej.mobwars

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.menu.ArenaSelectorMenu
import com.pepej.mobwars.service.ArenaService
import com.pepej.mobwars.service.UserService
import com.pepej.papi.command.Commands
import com.pepej.papi.services.Services
import com.pepej.papi.terminable.TerminableConsumer
import com.pepej.papi.terminable.module.TerminableModule
import com.pepej.papi.utils.TabHandlers
import java.util.*

class Commands : TerminableModule {

    private val arenaService = Services.load(ArenaService::class.java)
    private val userService = Services.load(UserService::class.java)


    override fun setup(consumer: TerminableConsumer) {
        Commands.parserRegistry().register(Arena::class.java) {
            Optional.ofNullable(arenaService.getArena(it))
        }

        Commands.create()
            .assertPlayer()
            .handler {
                ArenaSelectorMenu(it.sender()).open()
            }
            .registerAndBind(consumer, "arenas")

        arenaService.getArenas().map { it?.context()?.config()?.arenaId }
        Commands.create()
            .assertPlayer()
            .assertUsage("<arena>")
            .tabHandler { arenas() }
            .handler {
                val arena = it.arg(0).parseOrFail(Arena::class.java)
                val user = userService.getUserByPlayer(it.sender()) ?: return@handler
                arena.join(user)

            }
            .registerAndBind(consumer, "join")

    }


    private fun arenas(): List<String?> {
        return arenaService.getArenas()
            .map { it?.context()?.config()?.arenaId }

    }
}