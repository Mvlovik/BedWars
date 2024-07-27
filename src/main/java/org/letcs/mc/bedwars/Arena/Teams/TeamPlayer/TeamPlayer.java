package org.letcs.mc.bedwars.Arena.Teams.TeamPlayer;

import org.bukkit.Color;
import org.bukkit.Material;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;


public class TeamPlayer  {
    private final TeamBedWars team;
    private final Player p;
    private ItemStack[] armorItems;
    private ArmorType armorType = ArmorType.LEATHER;
    private PlayerState playerState = PlayerState.PLAYER;
    private boolean hasElytra = false;

    public TeamPlayer(TeamBedWars team, Player p) {
        this.team = team;
        this.p = p;
    }

    public ItemStack getTeamColorHelmet() {
        ItemStack is = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
        assert meta != null;
        meta.setColor(team.getColor());
        is.setItemMeta(meta);
       return is;
    }

    public void setStartInventory() {
        setArmor(ArmorType.LEATHER);
    }

    public void setDefaultArmor() {
        this.armorType = ArmorType.LEATHER;
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta em = elytra.getItemMeta();
        em.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        elytra.setItemMeta(em);

        armorItems = new ItemStack[]{
                new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.LEATHER_LEGGINGS),
                (hasElytra) ? elytra : new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_HELMET),
        };

        for (ItemStack is : armorItems) {
            if (is instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
                is.setItemMeta(meta);
            }
        }
        p.getInventory().setArmorContents(armorItems);
    }
    public void setArmor(ArmorType armorType) {
        this.armorType = armorType;
        if (armorType.equals(ArmorType.LEATHER)) {
            setDefaultArmor();
            return;
        }

        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta em = elytra.getItemMeta();
        em.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        elytra.setItemMeta(em);

        armorItems = new ItemStack[] {
                new ItemStack(Material.matchMaterial(armorType.name() + "_BOOTS")),
                new ItemStack(Material.matchMaterial(armorType.name() + "_LEGGINGS")),
                (hasElytra) ? elytra : new ItemStack(Material.matchMaterial(armorType.name() + "_CHESTPLATE")),
                getTeamColorHelmet()

        };
        p.getInventory().setArmorContents(armorItems);
    }

    public boolean isHasElytra() {
        return hasElytra;
    }

    public void setElytra(boolean hasElytra) {
        this.hasElytra = hasElytra;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
    public void teleportToBase() {
        p.teleport(team.getSpawnLocation());
    }

    public Color getTeamColor() {
        return team.getColor();
    }

    public Player getPlayer() {
        return p;
    }
}
