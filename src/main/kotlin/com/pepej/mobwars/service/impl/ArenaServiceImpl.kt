package com.pepej.mobwars.service.impl

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.service.ArenaService
import com.pepej.papi.utils.Log

class ArenaServiceImpl : ArenaService {

    private val arenas by lazy { hashSetOf<Arena>() }

    override fun register(arena: Arena) {
        if (arenas.any { it.context().config().arenaId == arena.context().config().arenaId }) {
            Log.info("Arena with given id(${arena.context().config().arenaId} already registered")
            arena.disable()
            arenas.remove(arena)
        }
        Log.info("registering")
        arenas.add(arena)
        arena.enable()
    }

    override fun unregister(id: String?) {
        arenas.remove(getArena(id))
    }

    override fun getArena(id: String?): Arena? {
       return arenas.find { it.context().config().arenaId == id }
    }

    override fun getMostRelevantArena(): Arena? {
        return arenas.filter(Arena::isActualToJoin).maxByOrNull { it.context().users().size }

    }

    override fun getArenas(): Set<Arena?> {
        return arenas
    }
}