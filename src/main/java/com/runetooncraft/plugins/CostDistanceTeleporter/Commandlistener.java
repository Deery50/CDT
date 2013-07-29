package com.runetooncraft.plugins.CostDistanceTeleporter;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandlistener implements CommandExecutor {
	Config config;
	public Commandlistener(Config config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(commandLabel.equalsIgnoreCase("cdt")) {
			if(args.length == 0) {
				Messenger.playermessage("Usage: /cdt <setprice>, /cdt <stats>, /cdt <price>", p);
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("price")) {pricecmd(p);}
				if(args[0].equalsIgnoreCase("stats")) {statscmd(p);}
			}else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("setprice")) {setprice(args[1],p);}
			}
		}
		return false;
	}

	private void statscmd(Player p) {
		if(CDT.perms.has(p, "cdt.stats")) {
		Messenger.playermessage("Amount of money currently spent on warps: " + config.getint("CDT.stats.money"), p);
		}else{
			nopermission(p);
		}
	}

	private void pricecmd(Player p) {
		if(CDT.perms.has(p, "cdt.price")) {
		Messenger.playermessage("Current price per block: " + config.getint("CDT.MoneyMultiplierPerblock"), p);
		}else{
			nopermission(p);
		}
	}

	private void setprice(String price, Player p) {
		if(CDT.perms.has(p, "cdt.setprice")) {
			if(isInteger(price)) {
				int priceint = Integer.parseInt(price);
				config.setint("CDT.MoneyMultiplierPerblock", priceint);
				Messenger.playermessage("Price per block set to " + priceint, p);
			}else{
				Messenger.playermessage("Price must be an integer.", p);
			}
		}else{
			nopermission(p);
		}
	}
	private void nopermission(Player p) {
		Messenger.playermessage("You do not have access to this command.", p);
	}

	public static boolean isInteger(String s) {
	    try {
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}

}
