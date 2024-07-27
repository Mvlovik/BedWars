package org.letcs.mc.bedwars.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class NPC {
    protected EntityPlayer npc;

    public NPC(Location loc, String name, String texture, String sign) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(Objects.requireNonNull(loc.getWorld()).getName()))).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        gameProfile.getProperties().put("textures", new Property("textures", texture, sign));

        npc = new EntityPlayer(server, world, gameProfile);
        npc.p(loc.getX(), loc.getY(), loc.getZ());
    }

    public void showToPlayer(Player p) {
        PlayerConnection connection = ((CraftPlayer) p).getHandle().c;

        connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
        connection.a(new PacketPlayOutNamedEntitySpawn(npc));

        Location loc = p.getLocation();
        CraftEntity.getEntity(((CraftServer) Bukkit.getServer()), npc).teleport(loc);
    }

    public void removeToPlayer(Player p) {
        PlayerConnection connection = ((CraftPlayer) p).getHandle().c;
        connection.a(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));

    }

    public void setLoc(Location loc) {
        npc.p(loc.getX(), loc.getY(), loc.getZ());
    }

    public EntityPlayer getNpc() {
        return npc;
    }
}
