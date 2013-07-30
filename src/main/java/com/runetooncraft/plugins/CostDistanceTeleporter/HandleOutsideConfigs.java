package com.runetooncraft.plugins.CostDistanceTeleporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class HandleOutsideConfigs {

	private YamlConfiguration outsideConfig;
	private File outconfig;
	
	public HandleOutsideConfigs(File file) {
		this.outsideConfig = new YamlConfiguration();
		this.outconfig = file;
	}
	public boolean load() {
		if (!outconfig.exists()) {
			Messenger.info(outconfig.getName() + " does not exist, disabling that function for now.");
			return false;
		}else{
			try {
				outsideConfig.load(outconfig);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	public YamlConfiguration getConfig() {
		return outsideConfig;
	}
}
