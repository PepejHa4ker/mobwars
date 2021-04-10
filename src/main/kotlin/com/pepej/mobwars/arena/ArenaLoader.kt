package com.pepej.mobwars.arena

interface ArenaLoader {

    fun unloadArena(arenaId: String)

    fun loadAndSaveArenaFromConfig(config: ArenaConfig)

    fun loadAndRegisterAllArenas();

}