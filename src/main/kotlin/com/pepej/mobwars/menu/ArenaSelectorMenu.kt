package com.pepej.mobwars.menu

import com.pepej.mobwars.service.ArenaService
import com.pepej.mobwars.utils.handle
import com.pepej.mobwars.utils.item
import com.pepej.mobwars.utils.meta
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.menu.Menu
import com.pepej.papi.menu.scheme.MenuScheme
import com.pepej.papi.services.Services
import org.bukkit.Material
import org.bukkit.entity.Player

class ArenaSelectorMenu(player: Player) : Menu(player, 3, "Выбор арены") {

    companion object {
        private val ARENA_SCHEME: MenuScheme = MenuScheme()
            .mask("001111100")
            .mask("001111100")
            .mask("001111100")
    }

    override fun redraw() {
        val arenaService = Services.load(ArenaService::class.java)
        val pop = ARENA_SCHEME.newPopulator(this)
        arenaService.getArenas().forEach {
            it ?: return@forEach
            pop.accept(
                item(Material.COMPASS)
                    .meta {
                        displayName = +"&a${it.context().config().arenaName}"
                    }
                    .handle(it::join)
            )
        }
    }
}