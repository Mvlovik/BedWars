package org.letcs.mc.bedwars.Arena.Teams.Improvements;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;

import java.util.ArrayList;

public class Improvements {
    private int swordImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
    private int toolImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
    private int armorImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
    private final TeamBedWars teamBedWars;

    public Improvements(TeamBedWars teamBedWars) {
        this.teamBedWars=teamBedWars;
    }

    public void setImproveToPlayer(Player p) {
        ItemStack[] contents = p.getInventory().getContents();
        for (ItemStack itemStack : contents) {
            if (itemStack == null) continue;
            if (swordImprove == ImproveLevel.ENCHANTMENT_LEVEL_MIN) continue;
            Material type = itemStack.getType();
            if (type.toString().contains("_SWORD")) {
                ItemMeta em = itemStack.getItemMeta();
                em.addEnchant(Enchantment.DAMAGE_ALL, swordImprove, true);
                itemStack.setItemMeta(em);
            }
        }
        for (ItemStack itemStack : contents) {
            if (itemStack == null) continue;
            if (toolImprove == ImproveLevel.ENCHANTMENT_LEVEL_MIN) continue;
            Material type = itemStack.getType();
            if (type.toString().contains("_PICKAXE") || type.toString().contains("_AXE") || type.equals(Material.SHEARS)) {
                ItemMeta em = itemStack.getItemMeta();
                em.addEnchant(Enchantment.DIG_SPEED, toolImprove, true);
                itemStack.setItemMeta(em);
            }
        }
        for (ItemStack itemStack : p.getInventory().getArmorContents()) {
            if (itemStack == null) continue;
            if (armorImprove == ImproveLevel.ENCHANTMENT_LEVEL_MIN) continue;
            ItemMeta em = itemStack.getItemMeta();
            em.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, armorImprove, true);
            itemStack.setItemMeta(em);

        }
    }

    public void restart() {
        swordImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
        toolImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
        armorImprove = ImproveLevel.ENCHANTMENT_LEVEL_MIN;
    }

    public void setImproveToPlayers() {
        for(Player p : teamBedWars.getPlayers()) {
            setImproveToPlayer(p);
        }
    }


    public boolean nextArmorImprove() {
        if (this.armorImprove + 1 > ImproveLevel.ENCHANTMENT_LEVEL_MAX) return false;
        this.armorImprove++;
        setImproveToPlayers();
        return true;
    }

    public boolean nextSwordImprove() {
        if (this.swordImprove + 1 > ImproveLevel.ENCHANTMENT_LEVEL_MAX) return false;
        this.swordImprove++;
        setImproveToPlayers();
        return true;
    }

    public boolean nextToolImprove() {
        if (this.toolImprove + 1 > ImproveLevel.ENCHANTMENT_LEVEL_MAX) return false;
        this.toolImprove++;
        setImproveToPlayers();
        return true;
    }

    public int getArmorImprove() {
        return armorImprove;
    }

    public int getSwordImprove() {
        return swordImprove;
    }

    public int getToolImprove() {
        return toolImprove;
    }
}
