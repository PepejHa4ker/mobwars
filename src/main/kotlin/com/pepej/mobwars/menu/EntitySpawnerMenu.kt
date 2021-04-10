package com.pepej.mobwars.menu

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.model.Mob
import com.pepej.mobwars.service.EntityService
import com.pepej.mobwars.utils.handle
import com.pepej.mobwars.utils.item
import com.pepej.mobwars.utils.meta
import com.pepej.mobwars.utils.unaryPlus
import com.pepej.papi.menu.Menu
import com.pepej.papi.menu.scheme.MenuScheme
import com.pepej.papi.services.Services
import org.bukkit.Material
import org.bukkit.entity.Player

class EntitySpawnerMenu(player: Player) : Menu(player, 3, "Отправщик мобов") {

    private val entityService = Services.load(EntityService::class.java)

    companion object {
        private val MENU_SCHEME: MenuScheme = MenuScheme()
            .mask("011111110")
            .mask("011111110")
            .mask("011111110")
    }


    override fun redraw() {
        val pop = MENU_SCHEME.newPopulator(this)
        pop.accept(
            item(Material.ARROW)
                .meta {
                    displayName = +"&aЗаслать моба"
                }
                .handle {
                    entityService.spawnEntity(
                        this ?: return@handle,
                        Mob("Мобик", 100, expCost = 100),
                        this.currentArena ?: return@handle,
                        Arena.TeamColor.RED
                    )
                }
        )
    }
}