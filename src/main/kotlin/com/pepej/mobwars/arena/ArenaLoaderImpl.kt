package com.pepej.mobwars.arena

import com.pepej.papi.config.BasicConfigurationNode
import com.pepej.papi.config.ConfigFactory
import java.io.File
import java.util.function.Predicate
import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.service.ArenaService

import com.pepej.papi.Papi
import com.pepej.papi.scoreboard.Scoreboard
import com.pepej.papi.scoreboard.ScoreboardProvider
import com.pepej.papi.services.Services
import com.pepej.papi.utils.Log

import org.bukkit.World


class ArenaLoaderImpl(private val file: File) : ArenaLoader {

    private val scoreboardProvider = Services.load(ScoreboardProvider::class.java)
    private val arenaService = Services.load(ArenaService::class.java)


    override fun loadAndSaveArenaFromConfig(config: ArenaConfig) {
        val node = ConfigFactory.gson().load(file);
        val arenaConfigs = node.getList(ArenaConfig::class.java) ?: return
        arenaConfigs.removeIf { it.arenaId == config.arenaId }
        arenaConfigs.add(config)
        node.setList(ArenaConfig::class.java, arenaConfigs)
        ConfigFactory.gson().save(file, node)
        registerArenaFromConfig(config)

    }

    override fun loadAndRegisterAllArenas() {
        val arenaConfigs = ConfigFactory.gson().load(file)
            .getList(ArenaConfig::class.java) ?: throw RuntimeException("Arena file not found!")
        for (arenaConfig in arenaConfigs) {
            registerArenaFromConfig(arenaConfig)
        }
    }

    private fun registerArenaFromConfig(config: ArenaConfig) {
        Log.info("loading arena ${config.arenaId}")
        val scoreboard: Scoreboard = scoreboardProvider.scoreboard
        val world = Papi.worldNullable(config.arenaWorld) ?: return
        val newArena: Arena = SingleArena(world, SingleArena.SingleArenaContext(config, scoreboard = scoreboard))
        arenaService.register(newArena)
    }

    override fun unloadArena(arenaId: String) {
        TODO("Not yet implemented")
    }
}