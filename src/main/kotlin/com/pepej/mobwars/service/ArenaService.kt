package com.pepej.mobwars.service

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.service.impl.ArenaServiceImpl
import com.pepej.papi.services.Implementor

@Implementor(ArenaServiceImpl::class)
interface ArenaService {

    fun register(arena: Arena)

    fun unregister(id: String?)

    fun getArena(id: String?): Arena?

    fun getMostRelevantArena(): Arena?

    fun getArenas(): Set<Arena?>
}