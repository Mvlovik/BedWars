package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.plugin.PluginManager;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Listener.OtherChips.*;
import org.letcs.mc.bedwars.Arena.Listener.OtherChips.SlimePlatform.AutoSlimePlatform;
import org.letcs.mc.bedwars.Arena.Listener.OtherChips.SlimePlatform.SlimePlatform;
import org.letcs.mc.bedwars.BedWars;

public class ArenaListener {
    private final Arena arena;
    public ArenaListener(Arena arena){
        this.arena = arena;
    }

    private PluginManager pluginManager;
    public void RegisterListeners() {
        pluginManager = BedWars.GetInstance().getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerRespawn(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new PlayerDeath(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new PlayerMove(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new onInventoryClick(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new BlockBreak(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new AutoBridge(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new Interact(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new TNT(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new TeleportToBase(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new SlimePlatform(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new DoubleJump(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new Mine(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new BlockBreakMine(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new EntityDamage(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new AutoSlimePlatform(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new GolemBedGuardian(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new TntBow(arena), BedWars.GetInstance());
        pluginManager.registerEvents(new FoodChangeLevel(arena), BedWars.GetInstance());
    }
}
