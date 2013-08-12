package com.runetooncraft.plugins.CostDistanceTeleporter;


import org.bukkit.Location;

public class parseLocation {
	private static Config config;
	public parseLocation(Config config){
		this.config = config;
	}
	public static int getDistanceCost(Location Loc1,Location Loc2) {
		if(Loc1.getWorld().equals(Loc2.getWorld())) {
				double distance = Loc1.distance(Loc2);
				int distanceint = (int) distance;
				int multivalue = config.getint("CDT.MoneyMultiplierPerblock");
				int out = multivalue * distanceint;
				return out;
			}else{
				if(config.getbool("CDT.MultiWorldSupport")) {
					if(config.getbool("CDT.EnableWorldChangeCost")) {
						int WorldChangeCost = config.getint("CDT.WorldChangeCost");
						Loc2 = ParseWorldLocation(Loc1, Loc2);
						double distance = Loc1.distance(Loc2);
						int distanceint = (int) distance;
						int multivalue = config.getint("CDT.MoneyMultiplierPerblock");
						int out = multivalue * distanceint;
						out = out + WorldChangeCost;
						return out;
					}else{
						Loc2 = ParseWorldLocation(Loc1, Loc2);
						double distance = Loc1.distance(Loc2);
						int distanceint = (int) distance;
						int multivalue = config.getint("CDT.MoneyMultiplierPerblock");
						int out = multivalue * distanceint;
						return out;
					}
				}else{
					return 0;
				}
			}
	}
	private static Location ParseWorldLocation(Location PlayerLocation, Location WarpLocation) {
		WarpLocation.setWorld(PlayerLocation.getWorld());
		return WarpLocation;
	}
}
