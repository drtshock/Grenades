package me.shock.grenades;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestListener implements Listener
{

	public final HashSet<Location> chestLoc = new HashSet<Location>();
	
    public Main plugin;
	
	public ChestListener(Main instance)
	{
		this.plugin = instance;
	}
	
	// Cancel block breakage on chest explosion.
	@EventHandler
	public void explodeEvent(EntityExplodeEvent event)
	{
		Location loc = event.getLocation();
		if(chestLoc.contains(loc))
		{
			event.blockList().clear();
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled stopping explosions");

		}
	}
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		
		if(action == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Material mat = block.getType();
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Right clicked block");
			
			if(mat == Material.CHEST)
			{
				int power = block.getBlockPower(BlockFace.SELF);
			    if(power > 1)
			    {
				    Location loc = block.getLocation();
				    loc.getWorld().createExplosion(loc, 2, true);
				    Bukkit.getServer().broadcastMessage(ChatColor.RED + "Chest blown up");
			    }
			}
		}
		
		if(action == Action.LEFT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Material mat = block.getType();
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Right clicked block");
			
			if(mat == Material.CHEST)
			{
				int power = block.getBlockPower(BlockFace.SELF);
			    if(power > 1)
			    {
				    Location loc = block.getLocation();
				    Player player = event.getPlayer();
				    player.sendMessage(ChatColor.RED + "Careful! That chest is boobie trapped!");
			    }
			}
		}
	}
	
	
	
	/*
	@EventHandler
	public void onPlace(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		// Check for the left click :P
		if(action == Action.LEFT_CLICK_BLOCK)
		{
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled left clicking block");
			Material matInHand = player.getItemInHand().getType();
			int amountInHand = player.getItemInHand().getAmount();
			Block block = event.getClickedBlock();
			Material blockMat = block.getType();

			
			// Get the click if it's a chest.
			if(blockMat == Material.CHEST)
			{
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled getting chest");

				// Arm chest with redstone in hand.
				if(matInHand == Material.REDSTONE)
				{
					Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled with redstone in hand");

					/*
					 * Cast block to chest.
					 * Get chest inventory and location.
					 
				    Chest chest = (Chest) event.getClickedBlock();
				    Inventory inv = chest.getBlockInventory();
				   // ItemStack[] invStack = chest.getInventory().getContents();
				    Location loc = chest.getLocation();
				    if(inv.contains(Material.SULPHUR, 8))
				    {
						Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled getting gun powder");
				    }
				    
				    // Arm chest.
				    if(inv.contains(Material.SULPHUR, 8) && (chestLoc.contains(loc) == false) && amountInHand == 8)
			    	{
						Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled arming chest");

					    chestLoc.add(loc);
					    player.sendMessage(ChatColor.RED + "Chest armed!");
				    }
				}
				
				// Disarm chest.
				if(matInHand == Material.SHEARS)
				{
					Location disarmLoc = block.getLocation();
					if(chestLoc.contains(disarmLoc))
					{
						Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled arming chest");

						chestLoc.remove(chestLoc);
						player.sendMessage(ChatColor.GREEN + "Chest disarmed!");
					}
				}
				
				/*
				 * Chest explosion :D
				 
				
				Location loc = block.getLocation();
				if(chestLoc.contains(loc) == true)
				{
					Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Handled explosion");

					loc.getWorld().createExplosion(loc, 4, false);
				}
			}
		}
	}
	*/
}
