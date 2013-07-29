package com.runetooncraft.plugins.CostDistanceTeleporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;



public class Config {

	private YamlConfiguration config;
	private File configFile;
	
	public Config(File configFile) {
        this.config = new YamlConfiguration();
        this.configFile = configFile;
	}
	
	public boolean load() {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
                getdefaults();
            }
            config.load(configFile);
            save();
            return true;
        }
        catch (Exception e) {
            Messenger.severe("Config Failed to load, returned error:\n" + e.getMessage());
            return false;
        }
	}
	private void getdefaults() {
		config.addDefault("CDT.prefix", "&a[CDT]&b");
		config.addDefault("CDT.MoneyMultiplierPerblock", 1);
		config.addDefault("CDT.Commandsenabled.warp", true);
		config.addDefault("CDT.stats.money", 0);
		config.options().copyDefaults(true);
		Messenger.info("Config Defaults loaded for the first time.");
	}

	public boolean save() {
		try {
			config.save(configFile);
		} catch (Exception e) {
            Messenger.severe("Config Failed to save, returned error: " + e.getMessage());
		}
		return true;
	}
public YamlConfiguration getConfig() {
	return config;
}
public void setstring(String path,String item){
	config.set(path, item);
	save();
}
public String parsestringcolors(String s) {
	return s.replaceAll("&([0-9a-f])", "\u00A7$1");
}
public String getstring(String path){
	return config.getString(path);
}
public boolean getbool(String path){
	return config.getBoolean(path);
}
public int getint(String path){
	return config.getInt(path);
}
public Location getlocation(String path){
	String worldstring = config.getString(path + ".world");
	World world = Bukkit.getWorld(worldstring);
	String locstring = config.getString(path + ".coords");
	Location loc = parselocation(locstring, world);
	return loc;
}
public Location getlocationbetter(String path) {
	String locstring = config.getString(path);
	Location loc = parseotherlocation(locstring);
	return loc;
}
public void setbool(String path,boolean item) {
	config.set(path, item);
	save();
}
public void setint(String path,int item) {
	config.set(path, item);
	save();
}
public void addint(String path,int item) {
	int int1 = getint(path);
	int int2 = int1 + item;
	setint(path, int2);
	save();
}
public void setlocation(String path,Location item,World world) {
	config.set(path + ".coords", locstring(item));
	config.set(path + ".world", world.getName());
	save();
}
public void setotherlocation(String path,Location item) {
	String l = locstringbetter(item);
	config.set(path, l);
	save();
}
public String returnlocstring(Location item) {
	return locstringbetter(item);
}
public String locstringbetter(Location l) {
	StringBuffer s = new StringBuffer();
	s.append(l.getBlockX() + ",");
	s.append(l.getBlockY() + ",");
	s.append(l.getBlockZ() + ",");
	s.append(l.getWorld().getName());
	return s.toString();
}

public static String locstring(Location l) {
	StringBuffer s = new StringBuffer();
	s.append(l.getBlockX() + ",");
	s.append(l.getBlockY() + ",");
	s.append(l.getBlockZ());
	return s.toString();
}
public static Location parselocation(String coordinates,World world) {
	String[] coords = coordinates.split(",");
	if (coords.length !=3) {
		throw new IllegalArgumentException("Invalid coordiantes for config parselocation.");
	}
	Integer x = parseint(coords[0]);
	Integer y = parseint(coords[1]);
	Integer z = parseint(coords[2]);
	return new Location(world, x, y, z);
}
public static Location parseotherlocation(String l) {
	String[] s = l.split(",");
	if (s.length !=4) {
		throw new IllegalArgumentException("Invalid coordiantes for config parseotherlocation.");
	}
	Integer x = parseint(s[0]);
	Integer y = parseint(s[1]);
	Integer z = parseint(s[2]);
	World world = Bukkit.getWorld(s[3]);
	return new Location(world, x, y, z);
}
public static Integer parseint(String i) {
	try {
		return Integer.parseInt(i.trim());
	}
	catch (Exception e) {
		return null;
	}
}
public void setlist(String path,ArrayList<String> list) {
	  List<String> configList = config.getStringList(path);
	    configList.addAll(list);
	    config.set(path, configList);
	    save();
}
public void addtolist(String path,String item) {
	List<String> clist = config.getStringList(path);
		clist.add(item);
		config.set(path, clist);
		save();
}
public List<String> getlist(String path) {
	List<String> returnlist = config.getStringList(path);
	return returnlist;
}
}
