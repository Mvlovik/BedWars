package org.letcs.mc.bedwars.Arena.MobInvasion;

import java.util.ArrayList;

public class MobInvasionWave {
    private final ArrayList<Mob> mobs;
    private final int duration;

    public MobInvasionWave(ArrayList<Mob> mobs, int duration) {
        this.mobs = mobs;
        this.duration = duration;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public int getDuration() {
        return duration;
    }
}
