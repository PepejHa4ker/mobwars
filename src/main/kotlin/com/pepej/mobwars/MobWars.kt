package com.pepej.mobwars

import com.pepej.mobwars.arena.ArenaLoader
import com.pepej.mobwars.arena.ArenaLoaderImpl
import com.pepej.mobwars.service.ArenaService
import com.pepej.mobwars.service.UserService
import com.pepej.mobwars.service.impl.UserServiceImpl
import com.pepej.papi.adventure.platform.bukkit.BukkitAudiences
import com.pepej.papi.ap.Plugin
import com.pepej.papi.ap.PluginDependency
import com.pepej.papi.plugin.PapiJavaPlugin


@Plugin(
    name = "mobwars",
    version = "1.0",
    authors = ["pepej"],
    description = "MobWars minecraft plugin",
    depends = [PluginDependency("papi")]
)
class MobWars : PapiJavaPlugin() {

    override fun onPluginEnable() {
        provideService(ArenaService::class.java)
        val audiences = BukkitAudiences.create(this)
        provideService(BukkitAudiences::class.java, bind(audiences))
        provideService(UserService::class.java, bindModule(UserServiceImpl()))
        val arenaLoader = ArenaLoaderImpl(getBundledFile("arenas.json"))
        provideService(ArenaLoader::class.java, arenaLoader)
        bindModule(Commands())
        arenaLoader.loadAndRegisterAllArenas()


    }
}


