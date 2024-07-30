package org.letcs.mc.bedwars.Arena.Teams;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.letcs.mc.bedwars.Arena.Teams.Improvements.Improvements;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.PlayerState;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.TeamColors;

import java.util.ArrayList;

public class TeamBedWars {
    private final Color color;
    private final ArrayList<TeamPlayer> teamPlayers = new ArrayList<>();
    private boolean bed_is_broken = false;
    private Location spawnLocation;
    private Location bedLocation;
    private final Improvements improvements;

    public TeamBedWars(Color color) {
        this.color = color;
        this.improvements = new Improvements(this);
    }

    public void addPlayer(TeamPlayer tP) {
        if(!teamPlayers.contains(tP)) teamPlayers.add(tP);
    }

    public ArrayList<Player> getPlayers() {
        final ArrayList<Player> players = new ArrayList<>();
        for (TeamPlayer teamPlayer : teamPlayers) {
            players.add(teamPlayer.getPlayer());
        }
        return players;
    }

    public void destroy() {
        restartBed();
        improvements.restart();
        bed_is_broken = false;
        teamPlayers.clear();
    }
    public boolean isBedLocation(Location loc) {
        Location loc1 = bedLocation.clone();
        loc1.setPitch(0);
        Location bedFront = bedLocation.clone().add(loc1.getDirection());
        return loc.equals(bedLocation.getBlock().getLocation()) || loc.equals(bedFront.getBlock().getLocation());
    }

    public TeamPlayer getTeamPlayerByPlayer(Player p) {
        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.getPlayer().equals(p)) return teamPlayer;
        }
        return null;
    }

    public void restartBed() {
        Material bedMat = Material.matchMaterial(TeamColors.getColorName(color) + "_BED");

        Location loc1 = bedLocation.clone();
        loc1.setPitch(0);
        Location bedFront = bedLocation.clone().add(loc1.getDirection());
        setBed(getBedLocation().getBlock(), bedFront.getBlock().getFace(bedLocation.getBlock()), bedMat);
        bed_is_broken = false;
    }

    public ArrayList<TeamPlayer> getAlivePlayers() {
        ArrayList<TeamPlayer> alivePlayers = new ArrayList<>();
        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.getPlayerState() == PlayerState.PLAYER) {
                alivePlayers.add(teamPlayer);
            }
        }
        return alivePlayers;
    }
    public void setBed(Block start, BlockFace facing, Material material) {
        for (Bed.Part part : Bed.Part.values()) {
            final Bed bedData = (Bed) Bukkit.createBlockData(material, (data) -> {
                ((Bed) data).setFacing(facing);
                ((Bed) data).setPart(part);
            });
            start.setBlockData(bedData);
            start = start.getRelative(facing.getOppositeFace());
        }
    }
    public void playSound(Sound sound) {
        getPlayers().forEach(player -> {
            player.playSound(player.getLocation(), sound, SoundCategory.PLAYERS, 1F, 1F);
        });
    }

    public Improvements getImprovements() {
        return improvements;
    }
    public void teleportToSpawn() {
        for (TeamPlayer tP : teamPlayers) tP.getPlayer().teleport(spawnLocation);
    }
    public void removePlayer(TeamPlayer tP) {
        teamPlayers.remove(tP);
    }
    public ArrayList<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }
    public Color getColor() {
        return this.color;
    }
    public boolean isBedIsBroken() {
        return bed_is_broken;
    }
    public void setBedLocation(Location bedLocation) {
        this.bedLocation = bedLocation;
    }
    public Location getBedLocation() {
        return bedLocation;
    }
    public void setBed_is_broken(boolean bed_is_broken) {
        this.bed_is_broken = bed_is_broken;
    }
    public Location getSpawnLocation() {
        return spawnLocation;
    }
    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
    public void sendMessageToTeam(String message) {
        getPlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }
}
