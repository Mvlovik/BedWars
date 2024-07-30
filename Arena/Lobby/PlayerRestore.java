package org.letcs.mc.bedwars.Arena.Lobby;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerRestore {
    private final int foodLevel, level;
    private final double healthscale, health;
    private final float xp;
    private final Location location;
    private final String displayName, tabName;
    private final boolean allowFlight, flying;
    private final ItemStack[] inventory;
    private final GameMode gamemode;
    private final UUID uuid;

    public PlayerRestore(UUID uuid) {
        this.uuid = uuid;

        Player p = Bukkit.getPlayer(uuid);

        assert p != null;
        this.level = p.getLevel();
        this.xp = p.getExp();
        this.health = p.getHealth();
        this.healthscale = p.getHealthScale();
        this.foodLevel = p.getFoodLevel();
        this.gamemode = p.getGameMode();
        this.allowFlight = p.getAllowFlight();
        this.flying = p.isFlying();
        this.tabName = p.getPlayerListName();
        this.displayName = p.getDisplayName();
        this.inventory = p.getInventory().getContents();
        this.location = p.getLocation();
        prepare();
    }
    public void prepare() {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) return;
        p.setExp(0);
        p.setLevel(0);
        p.setHealthScale(20);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(false);
        p.setFlying(false);
        setPlayerAttackSpeed(100);
    }

    public void restore() {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) return;

        setPlayerAttackSpeed(1);
        p.setAllowFlight(allowFlight);
        p.setFlying(flying);
        for (PotionEffect pf : p.getActivePotionEffects()) {
            p.removePotionEffect(pf.getType());
        }
        p.getInventory().setArmorContents(null);
        try {
            p.setHealth(health);
        } catch (Exception e){
            p.setHealth(20);
        }
        p.setFoodLevel(foodLevel);
        p.getInventory().setContents(inventory);
        p.setGameMode(gamemode);
        if (!displayName.equals(p.getDisplayName()))
            p.setDisplayName(displayName);
        if (!tabName.equals(p.getPlayerListName()))
            p.setPlayerListName(tabName);

        HashMap<ItemStack, Integer> items = null;
        p.setLevel(level);
        p.setExp(xp);
        p.getInventory().clear();
        p.setHealthScale(healthscale);
        p.teleport(location);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setPlayerAttackSpeed(int speed) {
        //double basic = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue();
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) return;
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(speed);
    }

}
