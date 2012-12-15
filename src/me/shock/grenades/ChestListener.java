package me.shock.grenades;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

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
		}
	}
	
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlace(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		// Check for the left click :P
		if(action == Action.LEFT_CLICK_BLOCK)
		{
			Material matInHand = player.getItemInHand().getType();
			int amountInHand = player.getItemInHand().getAmount();
			Block block = event.getClickedBlock();
			
			// Get the click if it's a chest.
			if(block instanceof Chest)
			{
				// Arm chest with redstone in hand.
				if(matInHand == Material.REDSTONE)
				{
				    Chest chest = (Chest) event.getClickedBlock();
				    Inventory inv = chest.getInventory();
				    Location loc = chest.getLocation();
				    
				    // Arm chest.
				    if(inv.contains(Material.SULPHUR, 8) && (chestLoc.contains(loc) == false) && amountInHand == 8)
			    	{
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
						chestLoc.remove(chestLoc);
						player.sendMessage(ChatColor.GREEN + "Chest disarmed!");
					}
				}
				
				/*
				 * Chest explosion :D
				 */
				
				Location loc = block.getLocation();
				if(chestLoc.contains(loc) == true)
				{
					loc.getWorld().createExplosion(loc, 4, false);
				}
			}
		}
	}
}
