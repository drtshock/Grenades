package me.shock.grenades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{

	protected FileConfiguration config;
	File file;
	
	public static Main plugin;
	
	Logger log = Bukkit.getServer().getLogger();
	
	public void onEnable()
	{
		
		PluginManager pm = getServer().getPluginManager();
		
		loadConfig();
		saveConfig();
	    pm.registerEvents(new LaunchListener(this), this);
	    pm.registerEvents(new ChestListener(this), this);
	    
	    //getServer().addRecipe(frag);
	    
	    startMetrics();
	    
	    loadRecipes();
	    
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
	
	public void loadRecipes()
	{
		
		/**
		 * Add frag grenade
		 * 2 tnt, 1 snowball
		 */
		ItemStack frag = new ItemStack(Material.SNOW_BALL, 1);
		ItemMeta fragMeta = frag.getItemMeta();
		ArrayList<String> fragLore = new ArrayList<String>();
		fragLore.add(ChatColor.RED + "Frag Grenade");
		fragMeta.setLore(fragLore);
		frag.setItemMeta(fragMeta);
		ShapelessRecipe fragRecipe = new ShapelessRecipe(frag).addIngredient(1, Material.SNOW_BALL).addIngredient(2, Material.TNT);
		getServer().addRecipe(fragRecipe);
		
		log.log(Level.INFO, "[SimpleGrenades] Frag recipe loaded.");
		
		/**
		 * Add flash grenade
		 * 1 tnt, 1 glowstone dust, 1 snowball
		 */
		ItemStack flash = new ItemStack(Material.SNOW_BALL, 1);
		ItemMeta flashMeta = frag.getItemMeta();
		ArrayList<String> flashLore = new ArrayList<String>();
		flashLore.add(ChatColor.RED + "Flash Grenade");
		flashMeta.setLore(flashLore);
		flash.setItemMeta(flashMeta);
		ShapelessRecipe flashRecipe = new ShapelessRecipe(frag).addIngredient(1, Material.SNOW_BALL)
				.addIngredient(1, Material.TNT).addIngredient(1, Material.GLOWSTONE_DUST);
		getServer().addRecipe(flashRecipe);
		
		log.log(Level.INFO, "[SimpleGrenades] Flash recipe loaded.");
		
	}
	
}
