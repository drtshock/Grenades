package me.shock.grenades;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestListener implements Listener
{

	public final HashSet<Location> chestLoc = new HashSet<Location>();
	
    public Main plugin;
	
	public ChestListener(Main instance)
	{
		this.plugin = instance;
	}
	
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		if(plugin.getConfig().getBoolean("Chest.RedstoneTrap.Enabled") == true)
		{
		Action action = event.getAction();
		
		if(action == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Material mat = block.getType();
			
			if(mat == Material.CHEST)
			{
				int power = block.getBlockPower(BlockFace.SELF);
			    if(power > 3)
			    {
				    Location loc = block.getLocation();
				    loc.getWorld().createExplosion(loc, 4F, true, false);
				    /*Added in the new api. source: http://jd.bukkit.org/dev/apidocs/org/bukkit/World.html#createExplosion(org.bukkit.Location, float, boolean, boolean)
				    * 4F == TNT
				    */
			    }
			}
		}
		
		if(action == Action.LEFT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Material mat = block.getType();
			
			if(mat == Material.CHEST)
			{
				int power = block.getBlockPower(BlockFace.SELF);
			    if(power > 3)
			    {
				    Player player = event.getPlayer();
				    player.sendMessage(ChatColor.RED + "Careful! That chest is boobie trapped!");
			    }
			}
		}
	}
		return;
	}
	
	/**
	 * Handle the pressure plate claymores.
	 */
	
	@EventHandler
	public void onStep(BlockRedstoneEvent event)
	{
		Location loc = event.getBlock().getLocation().add(0, 1, 0);
		int id = loc.getBlock().getTypeId();
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		boolean woodBreakBlocks = plugin.getConfig().getBoolean("Claymore.Wood.BreakBlocks");
		boolean stoneBreakBlocks = plugin.getConfig().getBoolean("Claymore.Stone.BreakBlocks");
		float woodPower = (float) plugin.getConfig().getInt("Claymore.Wood.Power");
		float stonePower = (float) plugin.getConfig().getInt("Claymore.Stone.Power");
		
		if(id == 70 && plugin.getConfig().getBoolean("Claymore.Stone.Enabled") == true)
		{
			loc.getWorld().createExplosion(x, y, z, stonePower, false, stoneBreakBlocks);
		}
		
		if(id == 72 && plugin.getConfig().getBoolean("Claymore.Wood.Enabled") == true)
		{
			loc.getWorld().createExplosion(x, y, z, woodPower, false, woodBreakBlocks);
		}
	}
}
