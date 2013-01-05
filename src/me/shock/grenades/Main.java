package me.shock.grenades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{

	private Commands executor;
	
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
	    
	    startMetrics();
	    loadRecipes();
	    loadCommands();
	    
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
		PluginDescriptionFile pdfFile = getDescription();
        String version = pdfFile.getVersion();	
		/**
		 * Check to see if there's a config.
		 * If not then create a new one.
		 */
		File config = new File(getDataFolder()+ "/config.yml");
		if(!config.exists())
		{
			try{
				config.createNewFile();
			} catch (IOException e) {
				log.log(Level.SEVERE, "[SimpleGreandes] Couldn't create config");
			}
			/**
			 * Write the config file here.
			 */
			try {
				String two = "  ";
				String four = "    ";
				
				BufferedWriter out = new BufferedWriter(new FileWriter(config, true));
				out.write("# SimpleGrenades by drtshock");
				out.newLine();
				out.write("# Visit www.dev.bukkit.org/server-mods/simplegrenades for help");
				out.newLine();
				out.write("# Version. Do not delete or change this or it will erase your config.");
				out.newLine();
				out.write("version: " + version);
				out.write("# Options for all grenades.");
				out.newLine();
				out.write("Grenades:");
				out.newLine();
				
				// Flash Grenade
				out.write(two + "Flash:");
				out.newLine();
				out.write(four + "radius: 5");
				out.newLine();
				out.write(four + "effectDuration: 5");
				out.newLine();
				out.write(four + "blindness: 1");
				out.newLine();
				out.write(four + "slowness: 2");
				out.newLine();
				out.write(four + "useTnT: false");
				out.newLine();
				
				// Frag Grenade
				out.write(two + "Frag:");
				out.newLine();
				out.write(four + "size: 1");
				out.newLine();
				out.write(four + "useTnT: false");
				out.newLine();
				
				// Concussion Grenade
				out.write(two + "Concussion:");
				out.newLine();
				out.write(four + "size: 1");
				out.newLine();
				out.write(four + "confusion: 2");
				out.newLine();
				out.write(four + "slowness: 2");
				out.newLine();
				out.write(four + "effectDuration");
				out.newLine();
				
				// Chest Trap
				out.write("Chest");
				out.newLine();
				out.write(two + "RedstoneTrap");
				out.newLine();
				out.write(four + "Enabled: false");
				out.newLine();
				
				// Claymores
				out.write("Claymore:");
				out.newLine();
				out.write(two + "Stone:");
				out.newLine();
				out.write(four + "Enabled: false");
				out.newLine();
				out.write(four + "BreakBlocks: false");
				out.newLine();
				out.write(four + "Power: 2");
				out.newLine();
				out.write(two + "Wood:");
				out.newLine();
				out.write(four + "Enabled: false");
				out.newLine();
				out.write(four + "BreakBlocks: false");
				out.newLine();
				out.write(four + "Power: 1");
				
				// Smoking gun
				out.close();
				
			} catch (IOException e) {
				log.log(Level.SEVERE, "[SimpleGrenades] Couldn't write config" + e);
			}
			
			
		}
		else if (version != plugin.config.getString("version"))
		{
			log.log(Level.SEVERE, "[SimpleGrenades] Your config is out of date. You may be missing key variables.");
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
	
	public void loadCommands()
	{
		executor = new Commands(this);
		getCommand("sg").setExecutor(executor);
	}
	
}
