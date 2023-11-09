package org.example.utils;


import java.util.HashMap;

public class Formatter {

    private final HashMap<String, String> colors = new Colors().getColors();

    private String prefix;
    private String suffix;
    private String separator;

    public String prompt(String string) {
        prefix = "> Enter ";
        suffix = ": ";
        return "\n" + setColor("blue",prefix + string + suffix);
    }
    public String menu1(String title) {
        prefix = " ##### ";
        suffix = " ##### ";
        return "\n" + setColor("black","white_bg",prefix + title + suffix);
    }
    public String menu2(String title) {
        prefix = " ### ";
        suffix = " ### ";
        return "\n" + setColor("black","white_bg",prefix + title + suffix);
    }
    public String menu3(String title) {
        prefix = " # ";
        suffix = " # ";
        return "\n" + setColor("black","white_bg",prefix + title + suffix);
    }
    public String description(String description, boolean reversed) {
        separator = " ";
        String order = reversed ? " (descending)" : " (ascending)";
        return separator + description + order;
    }
    public String setColor(String color, String string) {
        return colors.get(color.toUpperCase()) + string + colors.get("RESET");
    }
    public String setColor(String color, String bg, String string) {
        return colors.get(color.toUpperCase()) + colors.get(bg.toUpperCase()) + string + colors.get("RESET");
    }

}
