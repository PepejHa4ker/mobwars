package com.pepej.mobwars.utils

import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.UserService
import com.pepej.papi.item.ItemStackBuilder
import com.pepej.papi.menu.Item
import com.pepej.papi.services.Services
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


typealias MenuCallback = User?.() -> Unit

inline fun ItemStack.meta(block: ItemMeta.() -> Unit): ItemStack = apply { itemMeta = itemMeta.apply(block) }


inline fun item(material: Material, amount: Int = 1, data: Short = 0, meta: ItemMeta.() -> Unit = {}): ItemStack =
    ItemStack(material, amount, data).meta(meta)

inline fun ItemStack.handle(crossinline block: MenuCallback): Item = ItemStackBuilder.of(this).buildConsumer {
    val clicked = it.whoClicked as Player
    val userService = Services.load(UserService::class.java)
    block(userService.getUserByPlayer(clicked))
}