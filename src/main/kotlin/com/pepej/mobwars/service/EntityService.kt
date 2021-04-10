package com.pepej.mobwars.service

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.model.Boss
import com.pepej.mobwars.model.Entity
import com.pepej.mobwars.model.Step
import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.impl.EntityServiceImpl
import com.pepej.papi.services.Implementor

@Implementor(EntityServiceImpl::class)
interface EntityService {

    fun spawnEntity(spawner: User, entity: Entity, arena: Arena, color: Arena.TeamColor)

    fun getBossByStep(step: Step): Boss?

}