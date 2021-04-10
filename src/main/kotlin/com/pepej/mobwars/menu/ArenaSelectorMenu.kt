package com.pepej.mobwars.menu

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.service.ArenaService
import com.pepej.mobwars.service.UserService
import com.pepej.mobwars.utils.item
import com.pepej.mobwars.utils.meta
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.menu.Menu
import com.pepej.papi.menu.scheme.MenuScheme
import com.pepej.papi.services.Services
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta

class ArenaSelectorMenu(player: Player) : Menu(player, 3, "Выбор меню") {

    companion object {
        private val ARENA_SCHEME: MenuScheme = MenuScheme()
            .mask("001111100")
            .mask("001111100")
            .mask("001111100")
    }

    override fun redraw() {
        val arenaService = Services.load(ArenaService::class.java)
        val userService = Services.load(UserService::class.java)
        val user = userService.getUserByPlayer(player) ?: return
        val pop = ARENA_SCHEME.newPopulator(this)
        arenaService.getArenas().forEach {
            pop.accept(
                item(Material.DIAMOND)
                    .meta<ItemMeta> {
                        displayName = +"&a${it?.context()?.config()?.arenaName}"
                    }.item {
                        it?.join(user)
                    }
            )
        }
    }
}