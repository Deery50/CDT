package com.runetooncraft.plugins.CostDistanceTeleporter;

import java.io.File;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.IEssentials;

public class CDT extends JavaPlugin {
	Config config = null;
	public static IEssentials ess;
	public static Economy econ;
	public static Permission perms;
	public void onEnable() {
		loadconfig();
		checkessentials();
		checkvault();
		getServer().getPluginManager().registerEvents(new Teleportlistener(config), this);
		getCommand("cdt").setExecutor(new Commandlistener(config));
	}

	private void checkvault() {
		if(getServer().getPluginManager().getPlugin("Vault") == null) {
			Messenger.severe("Vault not found, CDT disabling");
			this.getServer().getPluginManager().disablePlugin(this);
		}else{
			Messenger.info("Found Vault");
			RegisteredServiceProvider<Economy> eco = getServer().getServicesManager().getRegistration(Economy.class);
			econ = eco.getProvider();
			 RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		    perms = rsp.getProvider();
		}
		
	}

	private void loadconfig() {
		File dir = this.getDataFolder();
		if (!dir.exists()) dir.mkdir();
		File file = new File(this.getDataFolder(), "config.yml");
		config = new Config(file);
		if (!config.load()) {
			this.getServer().getPluginManager().disablePlugin(this);
			throw new IllegalStateException("The config-file was not loaded correctly!");
		}
		passconfig();
//		config.save();	
	}

	private void passconfig() {
		Messenger m = new Messenger(config);
		parseLocation pl = new parseLocation(config);
	}

	private void checkessentials() {
		Plugin essPlugin;
		if(getServer().getPluginManager().isPluginEnabled("Essentials")) {
			Messenger.info("Found Essentials");
			essPlugin = Bukkit.getPluginManager().getPlugin("Essentials");
			ess = (IEssentials)essPlugin;
		}else{
			Messenger.severe("Essentials not found, CDT disabling");
			this.getServer().getPluginManager().disablePlugin(this);
		}
		
	}
}
