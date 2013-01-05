package me.shock.grenades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	   // loadCommands();
	    
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
        @SuppressWarnings("unused")
		String version = pdfFile.getVersion();	
		/**
		 * Check to see if there's a config.
		 * If not then create a new one.
		 */
		File config = new File(getDataFolder() + "/config.yml");
		if(!config.exists())
		{
			try{
				getDataFolder().mkdir();
				config.createNewFile();
			} catch (IOException e) {
				log.log(Level.SEVERE, "[SimpleGreandes] Couldn't create config");
			}
			/**
			 * Write the config file here.
			 * New, genius way to write it :)
			 */
			try {
				FileOutputStream fos = new FileOutputStream(new File(getDataFolder() + File.separator + "config.yml"));
				InputStream is = getResource("config.yml");
				byte[] linebuffer = new byte[4096];
				int lineLength = 0;
				while((lineLength = is.read(linebuffer)) > 0)
				{
					fos.write(linebuffer, 0, lineLength);
				}
				fos.close();
				
				log.log(Level.INFO, "[SimpleGrenades] Wrote new config");
				
			} catch (IOException e) {
				log.log(Level.SEVERE, "[SimpleGrenades] Couldn't write config: " + e);
			}	
		}
		else
		{
			log.log(Level.INFO, "[SimpleGrenades] Config found.");
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
