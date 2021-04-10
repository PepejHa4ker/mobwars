package com.pepej.mobwars.service.impl

import com.pepej.mobwars.api.Arena
import com.pepej.mobwars.model.Boss
import com.pepej.mobwars.model.Entity
import com.pepej.mobwars.model.Step
import com.pepej.mobwars.model.User
import com.pepej.mobwars.service.EntityService

class EntityServiceImpl : EntityService {

    private val entities: Map<Arena, Entity> = hashMapOf()

    override fun spawnEntity(spawner: User, entity: Entity, arena: Arena, color: Arena.TeamColor) {
        spawner.exp -= entity.expCost
        arena.broadcastMessage("Я типо спавню моба на самом деле нет для тимы $color")
    }

    override fun getBossByStep(step: Step): Boss? {
       return null
    }
}