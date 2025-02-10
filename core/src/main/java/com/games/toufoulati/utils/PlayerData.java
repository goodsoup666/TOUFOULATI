package com.games.toufoulati.utils;

public class PlayerData {
    private static String playerName = "Joueur";
    private static String playerGender = "Fille";
    private static int playerHP = 100;

    public static void setPlayerName(String name) {
        playerName = name;
    }
    public static void setPlayerGender(String gender) {
        playerGender = gender;
    }
    public static void setPlayerHP(int hp) {
        playerHP = hp;
    }

    public static String getPlayerName() {
        return playerName;
    }
    public static String getPlayerGender() {
        return playerGender;
    }
    public static int getPlayerHP() {
        return playerHP;
    }
}
