package com.pepej.mobwars

import com.pepej.papi.metadata.Metadata
import com.pepej.papi.metadata.MetadataKey
import com.pepej.papi.scoreboard.Scoreboard
import com.pepej.papi.scoreboard.ScoreboardObjective

val SCOREBOARD_KEY: MetadataKey<ScoreboardObjective> = MetadataKey.create("scoreboard", ScoreboardObjective::class.java)