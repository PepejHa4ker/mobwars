package com.pepej.mobwars.model

import java.time.Duration

abstract class Entity(
    open val name: String,
    open val maxHp: Int,
    open var hp: Int = maxHp,
    open val step: Step? = null,
    open val expCost: Int = 0
)

data class Step(
    val name: String,
    val ordinal: Int,
    val maxDuration: Duration,
    val remainingDuration: Duration = maxDuration
)

data class Boss(
    override val name: String,
    override val maxHp: Int,
    override var hp: Int,
    override val step: Step
) : Entity(name = name, maxHp = maxHp, hp = hp, step = step, expCost = 0)

data class Mob(
    override val name: String,
    override val maxHp: Int,
    override var hp: Int = maxHp,
    override val step: Step? = null,
    override val expCost: Int
) : Entity(name = name, maxHp = maxHp, hp = hp, step = step, expCost = 0)
