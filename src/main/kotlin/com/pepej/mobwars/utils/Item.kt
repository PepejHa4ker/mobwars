package com.pepej.mobwars.utils

import com.pepej.papi.item.ItemStackBuilder
import com.pepej.papi.menu.Item
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

typealias MenuCallback = InventoryClickEvent.() -> Unit

inline fun <reified T : ItemMeta> ItemStack.meta(block: T.() -> Unit): ItemStack =
    apply { itemMeta = (itemMeta as? T)?.apply(block) ?: itemMeta }

inline fun item(material: Material, amount: Int = 1, data: Short = 0, meta: ItemMeta.() -> Unit = {}): ItemStack =
    ItemStack(material, amount, data).meta(meta)

inline fun ItemStack.item(crossinline block: MenuCallback): Item = ItemStackBuilder.of(this).buildConsumer { block(it) }