package me.shock.grenades;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

	protected FileConfiguration config;
	File file;
	
	public static Main plugin;
	
	public void onEnable()
	{
		
		PluginManager pm = getServer().getPluginManager();
		
		loadConfig();
		saveConfig();
	    pm.registerEvents(new LaunchListener(this), this);
	    
	    startMetrics();
	    
	}
	
	public void startMetrics() {
		try {	
			Metrics metrics = new Metrics(this);	
			metrics.start();
		} catch (IOException e) {
			
		}
	}
	
	private void loadConfig()
	{
		this.file = new File(getDataFolder() + "/config.yml");
		
		getConfig().addDefault("Grenades.Flash.radius", 5);
		getConfig().addDefault("Grenades.Flash.effectDuration", 5);
		getConfig().addDefault("Grenades.Flash.blindness", 1);
		getConfig().addDefault("Grenades.Flash.slowness", 2);
		getConfig().addDefault("Grenades.Flash.useTnT", false);
		getConfig().addDefault("Grenades.Frag.size", 1);
		getConfig().addDefault("Grenades.Frag.useTnT", false);
		getConfig().addDefault("Grenades.Concussion.size", 1);
		getConfig().addDefault("Grenades.Concussion.confusion", 2);
		getConfig().addDefault("Grenades.Concussion.slowness", 2);
		getConfig().addDefault("Grenades.Concussion.effectDuration", 5);
		
		if (!this.file.exists())
		{	
			getConfig().options().copyDefaults(true);
		}
	}
}
