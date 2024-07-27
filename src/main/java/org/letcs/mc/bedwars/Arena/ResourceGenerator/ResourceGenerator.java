package org.letcs.mc.bedwars.Arena.ResourceGenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.Hologram;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceGenerator extends ResourceType implements Listener {
    private boolean isWork = false;
    private BukkitTask task = null;
    private int spawnTime;
    private Hologram name;
    private Hologram timeSpawnHologram;
    private final ArrayList<Item> resources = new ArrayList<>();
    private final Random rand = new Random();
    private Sound soundPickup = null;
    private final Hologram timerHologram;
    private Hologram nameHologram;
    private final Location loc;
    private boolean pickupToInventory;

    public ResourceGenerator(Location loc, int spawnTime, ItemStack itemStack, String name, int price, boolean pickupToInventory) {
        super(itemStack, price);
        this.loc = loc;
        this.spawnTime = spawnTime;
        this.timerHologram = new Hologram(loc.clone().add(0, -0.3, 0), "0c");
        this.timerHologram.setCustomVisible(false);
        this.pickupToInventory = pickupToInventory;
        if (name != null)
            nameHologram = new Hologram(loc, name);
        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());

    }

    public void setTimer(Boolean bool) {
        timerHologram.setCustomVisible(bool);
    }

    public void remove() {
        resources.clear();
        for (Entity entity : timerHologram.getLocation().getChunk().getEntities()) {
            if (entity instanceof ArmorStand) {
                if (entity.hasMetadata("MvlovikBW")) {
                    Bukkit.getScheduler().runTask(BedWars.GetInstance(), entity::remove);
                }
            }
        }
        if (task !=null) task.cancel();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!isWork) return;
        if (e.getBlock().getLocation().distance(loc.getBlock().getLocation()) < 3) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Вы не можете ставить здесь блоки.");
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player && resources.contains(e.getItem())) {
            Player p = (Player) e.getEntity();

            if (rand.nextInt(10) == 1) {
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
            } else {
                if(this.soundPickup != null)
                    p.playSound(p.getLocation(), this.soundPickup, 1f, 1f);
            }
            if (!pickupToInventory) {
                e.setCancelled(true);
                p.setLevel(p.getLevel()+super.getPrice()*e.getItem().getItemStack().getAmount());
                e.getItem().remove();
            }
        }
    }

    public void setSoundPickup(Sound soundPickup) {
        this.soundPickup = soundPickup;
    }

    public void startSpawn() {
        if (isWork) return;

        ItemStack itemStack = super.getItemStack();
        task = Bukkit.getScheduler().runTaskTimer(BedWars.GetInstance(), new Runnable() {
            int time = 0; //Счётчик времени

            @Override
            public void run() {
                if (time >= spawnTime) {
                    Item randomItem = loc.getWorld().dropItem(loc.clone(), itemStack);

                    randomItem.setVelocity(new Vector(0, 0, 0));
                    resources.add(randomItem);
                    time = 0;
                }

                double timeForDrop = (double) (spawnTime - time) / 20;
                timerHologram.replaceText(Math.floor(timeForDrop * 10) / 10 + "c");

                time++;

            }
        }, 0L, 1L);
        isWork = true;
    }

    public void setPickupToInventory(boolean pickupToInventory) {
        this.pickupToInventory = pickupToInventory;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
    public void stopSpawn() {
        if (isWork) task.cancel();
        isWork = false;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public Hologram getHologramName() {
        return nameHologram;
    }

    public Hologram getHologramTimer() {
        return timerHologram;
    }
}
