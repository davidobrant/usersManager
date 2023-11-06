package org.example;


import java.util.HashMap;


public class Colors {

    public HashMap<String, String> getColors() {
        HashMap<String, String> colors = new HashMap<>();
        colors.put("RESET", "\u001B[0m");
        colors.put("BLACK", "\u001B[30m");
        colors.put("RED", "\u001B[31m");
        colors.put("GREEN", "\u001B[32m");
        colors.put("YELLOW", "\u001B[33m");
        colors.put("BLUE", "\u001B[34m");
        colors.put("PURPLE", "\u001B[35m");
        colors.put("CYAN", "\u001B[36m");
        colors.put("WHITE", "\u001B[37m");
        colors.put("BLACK_BG", "\u001B[40m");
        colors.put("RED_BG", "\u001B[41m");
        colors.put("GREEN_BG", "\u001B[42m");
        colors.put("YELLOW_BG", "\u001B[43m");
        colors.put("BLUE_BG", "\u001B[44m");
        colors.put("PURPLE_BG", "\u001B[45m");
        colors.put("CYAN_BG", "\u001B[46m");
        colors.put("WHITE_BG", "\u001B[47m");
        return colors;
    }
}
