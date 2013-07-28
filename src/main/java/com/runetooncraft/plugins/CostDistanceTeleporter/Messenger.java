package com.runetooncraft.plugins.CostDistanceTeleporter;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger {
	
    private static final Logger log = Logger.getLogger("Minecraft");
    static Config config;
    private static final String prefix = "[CDT] ";
    private static String colorprefix;
   
    public Messenger(Config config) {
    	this.config = config;
    	if(config.load()) {
    	colorprefix = config.parsestringcolors(config.getstring("CDT.prefix")) + " ";
    	}
    }
    
    public static void severe(String msg) {
        log.severe(prefix + msg);
    }
    public static void info(String msg) {
        log.info(prefix + msg);
    }
    public static void broadcast(String msg){
    	Bukkit.broadcastMessage(colorprefix + msg);
    }
    public static void playermessage(String msg, Player p){
    	p.sendMessage(colorprefix + msg);
    }
    public static void playerlistmessagestring(String msg,ArrayList<String> Players){
        for (String player : Players) {
            Bukkit.getPlayerExact(player).sendMessage(msg);
        }
    }
    public static void playerlistmessage(String msg,ArrayList<Player> Players){
        for (Player p : Players) {
            p.sendMessage(msg);
        }
    }
}
