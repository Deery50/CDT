package com.runetooncraft.plugins.CostDistanceTeleporter;

import org.bukkit.Location;

public class parseLocation {
	private static Config config;
	public parseLocation(Config config){
		this.config = config;
	}
	public static int getDistanceCost(Location Loc1,Location Loc2) {
		double distance = Loc1.distance(Loc2);
		int distanceint = (int) distance;
		int multivalue = config.getint("CDT.MoneyMultiplierPerblock");
		int out = multivalue * distanceint;
		return out;
	}
}
