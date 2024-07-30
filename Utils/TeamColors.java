package org.letcs.mc.bedwars.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.letcs.mc.bedwars.Arena.Teams.TeamColor;

import java.util.ArrayList;
import java.util.List;

public class TeamColors {
    public static String getColorName(Color color) {
        if (color.equals(Color.GREEN)) return "GREEN";
        if(color.equals(Color.BLACK)) return "BLACK";
        if(color.equals(Color.YELLOW)) return "YELLOW";
        if(color.equals(Color.ORANGE)) return "ORANGE";
        if(color.equals(Color.BLUE)) return "BLUE";
        if(color.equals(Color.GRAY)) return "GRAY";
        if(color.equals(Color.RED)) return "RED";
        if(color.equals(Color.AQUA)) return "LIGHT_BLUE";
        if(color.equals(Color.LIME)) return "LIME";
        if(color.equals(Color.PURPLE)) return "PURPLE";
        return null;
    }
    public static Color getColorByString(String colorString) {
        if (colorString.equals("RED")) return Color.RED;
        if (colorString.equals("BLACK")) return Color.BLACK;
        if (colorString.equals("BLUE")) return Color.BLUE;
        if (colorString.equals("GRAY")) return Color.GRAY;
        if (colorString.equals("AQUA")) return Color.AQUA;
        if (colorString.equals("PURPLE")) return Color.PURPLE;
        if (colorString.equals("ORANGE")) return Color.ORANGE;
        if (colorString.equals("GREEN")) return Color.GREEN;
        if (colorString.equals("LIME")) return Color.LIME;
        if(colorString.equals("YELLOW")) return Color.YELLOW;
        return null;
    }
    public static Color getColorByColorTeam(String colorString) {
        if (colorString.equals("RED")) return Color.RED;
        if (colorString.equals("BLACK")) return Color.BLACK;
        if (colorString.equals("BLUE")) return Color.BLUE;
        if (colorString.equals("GRAY")) return Color.GRAY;
        if (colorString.equals("LIGHT_BLUE")) return Color.AQUA;
        if (colorString.equals("ORANGE")) return Color.ORANGE;
        if (colorString.equals("GREEN")) return Color.GREEN;
        if (colorString.equals("LIME")) return Color.LIME;
        if (colorString.equals("PURPLE")) return Color.PURPLE;
        if(colorString.equals("YELLOW")) return Color.YELLOW;
        return null;
    }
    public static List<Color> getAllColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.LIME);
        colors.add(Color.GREEN);
        colors.add(Color.AQUA);
        colors.add(Color.BLUE);
        colors.add(Color.PURPLE);
        colors.add(Color.BLACK);
        return colors;
    }
    public static ArrayList<String> getAllColorsNamesMaterial() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("RED");
        colors.add("ORANGE");
        colors.add("YELLOW");
        colors.add("LIME");
        colors.add("GREEN");
        colors.add("LIGHT_BLUE");
        colors.add("BLUE");
        colors.add("PURPLE");
        colors.add("BLACK");
        return colors;
    }
    public static String getTeamColorName(Color color) {
        if (color.equals(Color.RED)) return "команда красных";
        if (color.equals(Color.ORANGE)) return "команда оранживых";
        if (color.equals(Color.YELLOW)) return "команда жёлтых";
        if (color.equals(Color.LIME)) return "команда лаймовых";
        if (color.equals(Color.GREEN)) return "команда зелёных";
        if (color.equals(Color.AQUA)) return "команда ♂голубых♂";
        if (color.equals(Color.BLUE)) return "команды синих";
        if (color.equals(Color.PURPLE)) return "команды фиолетовых";
        if (color.equals(Color.BLACK)) return "команда чёрных";
        return null;
    }
    public static String getTeamColorName1(Color color) {
        if (color.equals(Color.RED)) return "команде красных";
        if (color.equals(Color.ORANGE)) return "команде оранживых";
        if (color.equals(Color.YELLOW)) return "команде жёлтых";
        if (color.equals(Color.LIME)) return "команде лаймовых";
        if (color.equals(Color.GREEN)) return "команде зелёных";
        if (color.equals(Color.AQUA)) return "команде ♂голубых♂";
        if (color.equals(Color.BLUE)) return "команде синих";
        if (color.equals(Color.PURPLE)) return "команде фиолетовых";
        if (color.equals(Color.BLACK)) return "команде чёрных";
        return null;
    }
    public static ChatColor getChatColorByColor(Color color) {
        if (color.equals(Color.RED)) return ChatColor.RED;
        if (color.equals(Color.ORANGE)) return ChatColor.GOLD;
        if (color.equals(Color.YELLOW)) return ChatColor.YELLOW;
        if (color.equals(Color.LIME)) return ChatColor.GREEN;
        if (color.equals(Color.GREEN)) return ChatColor.DARK_GREEN;
        if (color.equals(Color.AQUA)) return ChatColor.AQUA;
        if (color.equals(Color.BLUE)) return ChatColor.BLUE;
        if (color.equals(Color.PURPLE)) return ChatColor.LIGHT_PURPLE;
        if (color.equals(Color.BLACK)) return ChatColor.BLACK;
        return null;
    }
    public static Material getBedByColor(Color color) {
        return Material.matchMaterial(getColorName(color) + "_BED");
    }
}
