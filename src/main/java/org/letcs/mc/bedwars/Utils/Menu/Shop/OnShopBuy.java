package org.letcs.mc.bedwars.Utils.Menu.Shop;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Utils.Menu.Shop.ShopPosition;

public class OnShopBuy extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private final ShopPosition position;
    private boolean cancelled = false;
    public OnShopBuy(Player player, ShopPosition position) {
        this.player = player;
        this.position = position;
    }

    public ShopPosition getShopPosition() {
        return position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
