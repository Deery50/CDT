package com.runetooncraft.plugins.CostDistanceTeleporter;

import java.util.HashMap;

import net.ess3.api.InvalidWorldException;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.commands.WarpNotFoundException;

public class Teleportlistener implements Listener {
	private Config config;
	public static HashMap<Player, String> Playerwarpdata = new HashMap<Player, String>();
	public Teleportlistener(Config config){
		this.config = config;
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if(event.getMessage().startsWith("/warp") && config.getbool("CDT.Commandsenabled.warp")) {
		Player p = event.getPlayer();
		String input = event.getMessage();
		String[] args = input.split(" ");
		if(args[0].equals("/warp") && args.length == 2) {
			if(CDT.perms.has(p, "cdt.bypass") == false) {
			if(CDT.perms.has(p, "essentials.warps." + args[1])) {
				try {
					String Cvalue = CDT.econ.currencyNamePlural();
					Location warploc = CDT.ess.getWarps().getWarp(args[1]).getBlock().getLocation();
					int Cost = parseLocation.getDistanceCost(p.getLocation(), warploc);
					if(Playerwarpdata.get(p) == null) {
						Messenger.playermessage("Warping to " + args[1] + " will cost " + Cost + " " + Cvalue + ". Type [/warp " + args[1] + "] again to pay this amount and teleport.", p);
						Playerwarpdata.put(p, args[1]);
						event.setCancelled(true);
					}else{
					if(Playerwarpdata.get(p).equals(args[1])) {
							if(CDT.econ.has(p.getName(), Cost)) {
								EconomyResponse r = CDT.econ.withdrawPlayer(p.getName(), Cost);
								Messenger.playermessage(Cost + " " + Cvalue + " was deducted from your account to warp to " + args[1], p);
								config.addint("CDT.stats.money", Cost);
								Playerwarpdata.remove(p);
								event.setCancelled(false);
							}else{
								Messenger.playermessage("You do not have sufficient funds to warp to " + args[1] + ". You require " + Cost + " " + Cvalue + ", but only have " + CDT.econ.getBalance(p.getName()) + " " + Cvalue + ".", p);
								Playerwarpdata.remove(p);
								event.setCancelled(true);
							}
							//TODO: Currency check (if player has enough money for warp), Taking money away and warping, clear hashmap of this value.
						}else{
							Messenger.playermessage("Warping to " + args[1] + " will cost " + Cost + " " + Cvalue + ". Type [/warp " + args[1] + "] again to pay this amount and teleport.", p);
							Playerwarpdata.put(p, args[1]);
							event.setCancelled(true);
						}
					}
				} catch (WarpNotFoundException e) {
					Messenger.playermessage("Warp " + args[1] + " not found.", p);
				} catch (InvalidWorldException e) {
					Messenger.playermessage("Invalid world.", p);
				}
			}else{
				//essentials tells them they don't have permission
			}
			}else{
				//bypass CostDistanceTeleporter
				event.setCancelled(false);
			}
		}
	}
	}
}
