package com.pepej.mobwars.arena;


import com.pepej.papi.config.objectmapping.ConfigSerializable;
import com.pepej.papi.config.objectmapping.meta.Setting;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigSerializable
public class ArenaConfig {

    @Setting
    private
    String arenaId;

    @Setting
    private
    String arenaName;

    @Setting
    private
    String arenaWorld;

    @Setting
    private
    int maxPlayers;

    @Setting
    private
    Duration arenaStartDelay;

    @Setting
    private
    Duration arenaGameTime;

    public ArenaConfig(String arenaId, String arenaName, String arenaWorld, int maxPlayers, Duration arenaStartDelay, Duration arenaGameTime) {
        this.arenaId = arenaId;
        this.arenaName = arenaName;
        this.arenaWorld = arenaWorld;
        this.maxPlayers = maxPlayers;
        this.arenaStartDelay = arenaStartDelay;
        this.arenaGameTime = arenaGameTime;
    }

    public ArenaConfig() {
    }

    public static ArenaConfigBuilder builder() {
        return new ArenaConfigBuilder();
    }

    public String getArenaId() {
        return this.arenaId;
    }

    public String getArenaName() {
        return this.arenaName;
    }

    public String getArenaWorld() {
        return this.arenaWorld;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public Duration getArenaStartDelay() {
        return this.arenaStartDelay;
    }

    public Duration getArenaGameTime() {
        return this.arenaGameTime;
    }

    public void setArenaId(String arenaId) {
        this.arenaId = arenaId;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public void setArenaWorld(String arenaWorld) {
        this.arenaWorld = arenaWorld;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setArenaStartDelay(Duration arenaStartDelay) {
        this.arenaStartDelay = arenaStartDelay;
    }

    public void setArenaGameTime(Duration arenaGameTime) {
        this.arenaGameTime = arenaGameTime;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ArenaConfig)) {
            return false;
        }
        final ArenaConfig other = (ArenaConfig) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$arenaId = this.getArenaId();
        final Object other$arenaId = other.getArenaId();
        if (this$arenaId == null ? other$arenaId != null : !this$arenaId.equals(other$arenaId)) {
            return false;
        }
        final Object this$arenaName = this.getArenaName();
        final Object other$arenaName = other.getArenaName();
        if (this$arenaName == null ? other$arenaName != null : !this$arenaName.equals(other$arenaName)) {
            return false;
        }
        final Object this$arenaWorld = this.getArenaWorld();
        final Object other$arenaWorld = other.getArenaWorld();
        if (this$arenaWorld == null ? other$arenaWorld != null : !this$arenaWorld.equals(other$arenaWorld)) {
            return false;
        }
        if (this.getMaxPlayers() != other.getMaxPlayers()) {
            return false;
        }
        final Object this$arenaStartDelay = this.getArenaStartDelay();
        final Object other$arenaStartDelay = other.getArenaStartDelay();
        if (this$arenaStartDelay == null ? other$arenaStartDelay != null : !this$arenaStartDelay.equals(other$arenaStartDelay)) {
            return false;
        }
        final Object this$arenaGameTime = this.getArenaGameTime();
        final Object other$arenaGameTime = other.getArenaGameTime();
        if (this$arenaGameTime == null ? other$arenaGameTime != null : !this$arenaGameTime.equals(other$arenaGameTime)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ArenaConfig;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $arenaId = this.getArenaId();
        result = result * PRIME + ($arenaId == null ? 43 : $arenaId.hashCode());
        final Object $arenaName = this.getArenaName();
        result = result * PRIME + ($arenaName == null ? 43 : $arenaName.hashCode());
        final Object $arenaWorld = this.getArenaWorld();
        result = result * PRIME + ($arenaWorld == null ? 43 : $arenaWorld.hashCode());
        result = result * PRIME + this.getMaxPlayers();
        final Object $arenaStartDelay = this.getArenaStartDelay();
        result = result * PRIME + ($arenaStartDelay == null ? 43 : $arenaStartDelay.hashCode());
        final Object $arenaGameTime = this.getArenaGameTime();
        result = result * PRIME + ($arenaGameTime == null ? 43 : $arenaGameTime.hashCode());
        return result;
    }

    public String toString() {
        return "ArenaConfig(arenaId=" + this.getArenaId() + ", arenaName=" + this.getArenaName() + ", arenaWorld=" + this.getArenaWorld() + ", maxPlayers=" + this.getMaxPlayers() + ", arenaStartDelay=" + this.getArenaStartDelay() + ", arenaGameTime=" + this.getArenaGameTime() + ")";
    }

    public static class ArenaConfigBuilder {
        private String arenaId;
        private String arenaName;
        private String arenaWorld;
        private int maxPlayers;
        private Duration arenaStartDelay;
        private Duration arenaGameTime;

        ArenaConfigBuilder() {
        }

        public ArenaConfigBuilder arenaId(String arenaId) {
            this.arenaId = arenaId;
            return this;
        }

        public ArenaConfigBuilder arenaName(String arenaName) {
            this.arenaName = arenaName;
            return this;
        }

        public ArenaConfigBuilder arenaWorld(String arenaWorld) {
            this.arenaWorld = arenaWorld;
            return this;
        }

        public ArenaConfigBuilder maxPlayers(int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }

        public ArenaConfigBuilder arenaStartDelay(Duration arenaStartDelay) {
            this.arenaStartDelay = arenaStartDelay;
            return this;
        }

        public ArenaConfigBuilder arenaGameTime(Duration arenaGameTime) {
            this.arenaGameTime = arenaGameTime;
            return this;
        }

        public ArenaConfig build() {
            return new ArenaConfig(arenaId, arenaName, arenaWorld, maxPlayers, arenaStartDelay, arenaGameTime);
        }

        public String toString() {
            return "ArenaConfig.ArenaConfigBuilder(arenaId=" + this.arenaId + ", arenaName=" + this.arenaName + ", arenaWorld=" + this.arenaWorld + ", maxPlayers=" + this.maxPlayers + ", arenaStartDelay=" + this.arenaStartDelay + ", arenaGameTime=" + this.arenaGameTime + ")";
        }
    }
}
