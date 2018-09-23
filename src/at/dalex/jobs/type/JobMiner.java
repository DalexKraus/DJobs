package at.dalex.jobs.type;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import at.dalex.jobs.Jobs;

public class JobMiner implements IJobType {

	private final int POINTS_DIAMOND_ORE = 16;
	private final int POINTS_GOLD_ORE = 12;
	private final int POINTS_IRON_ORE = 4;
	private final int POINTS_COAL_ORE = 2;

	private final float POINTS_PER_STONE = 1 / 8;

	private HashMap<UUID, Integer> stoneCounter = new HashMap<>();

	public JobMiner() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {

		if (Jobs.getInstance().getJobManager().getJob(e.getPlayer().getUniqueId()) == EnumJob.MINER) {
			int points = 0;

			if (e.getBlock().getType() == Material.DIAMOND_ORE) {
				points = POINTS_DIAMOND_ORE;
			}
			else if (e.getBlock().getType() == Material.GOLD_ORE) {
				points = POINTS_GOLD_ORE;
				dropItemAt(Material.GOLD_INGOT, e.getBlock().getLocation());
			}
			else if (e.getBlock().getType() == Material.IRON_ORE) {
				points = POINTS_IRON_ORE;
				dropItemAt(Material.IRON_INGOT, e.getBlock().getLocation());
			}
			else if (e.getBlock().getType() == Material.COAL_ORE) {
				points = POINTS_COAL_ORE;
			}
			else if (e.getBlock().getType() == Material.STONE) {
				increaseStoneCounter(e.getPlayer().getUniqueId());
				if ((stoneCounter.get(e.getPlayer().getUniqueId()) != null) && stoneCounter.get(e.getPlayer().getUniqueId()) > 4) {
					points = convertStoneToPoints(e.getPlayer().getUniqueId());
					stoneCounter.put(e.getPlayer().getUniqueId(), 0);
				}
			}
			Jobs.getInstance().getJobManager().addPoint(e.getPlayer().getUniqueId(), points);
			

		}
	}

	private int convertStoneToPoints(UUID player) {
		int stoneCounter = 1;
		if (this.stoneCounter.get(player) != null) {
			stoneCounter += this.stoneCounter.get(player);
		}

		return (int) (stoneCounter * POINTS_PER_STONE);
	}

	private void increaseStoneCounter(UUID player) {
		int stoneCounter = 1;
		if (this.stoneCounter.get(player) != null) {
			stoneCounter += this.stoneCounter.get(player);
		}
		this.stoneCounter.put(player, stoneCounter);
	}
	
	private void dropItemAt(Material material, Location location) {
		ItemStack itemStack = new ItemStack(material, 1);
		location.getWorld().dropItemNaturally(location, itemStack);
	}
}
