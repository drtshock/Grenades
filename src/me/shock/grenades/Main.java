package me.shock.grenades;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
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
	    pm.registerEvents(new ChestListener(this), this);
	    
	    //getServer().addRecipe(frag);
	    
	    startMetrics();
	    
	}
	
	public void onDisable()
	{
		
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
		getConfig().addDefault("Chest.RedstoneTrap.Enabled", false);
		getConfig().addDefault("Claymore.Stone.Enabled", false);
		getConfig().addDefault("Claymore.Stone.BreakBlocks", false);
		getConfig().addDefault("Claymore.Stone.Power", 2);
		getConfig().addDefault("Claymore.Wood.Enabled", false);
		getConfig().addDefault("Claymore.Wood.BreakBlocks", false);
		getConfig().addDefault("Claymore.Wood.Power", 1);
		
		if (!this.file.exists())
		{	
			getConfig().options().copyDefaults(true);
		}
	}
	
	/*
	ItemMeta meta = new ItemStack(Material.SNOW_BALL, 1).getItemMeta().setDisplayName("frag");
	ShapelessRecipe frag = new ShapelessRecipe(new ItemStack(Material.SNOW_BALL, 1).setItemMeta(meta)).addIngredient(Material.SNOW_BALL).addIngredient(Material.TNT)

	@EventHandler
	public void onCraft(CraftItemEvent event)
	{
		
		ItemStack snowball = new ItemStack(Material.SNOW_BALL, 1);
		
		if(event.getRecipe() instanceof ShapelessRecipe)
		{
			if(event.getRecipe() == frag)
			{
				event.setResult(Result.ALLOW);
				ItemMeta meta = snowball.getItemMeta();
				meta.setDisplayName(ChatColor.RED + "Frag");
				event.getInventory().setResult(snowball);

			}
		}
	}
	*/
	
}
