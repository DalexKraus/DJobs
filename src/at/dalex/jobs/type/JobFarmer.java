package at.dalex.jobs.type;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import at.dalex.jobs.Jobs;

public class JobFarmer implements IJobType {

	private final int POINTS_PER_GROWN_SEED = 2;
	private final int f = 2;
	
	public JobFarmer() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) == EnumJob.FARMER) {
			if (isHarvestableBlock(e.getBlock().getType())) {
				if (isFullyGrown(e.getBlock())) {
					Jobs.getInstance().getJobManager().addPoint(p.getUniqueId(), POINTS_PER_GROWN_SEED);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) == EnumJob.FARMER) {
			if (isHarvestableBlock(e.getBlock().getType())) {
				
				
//				Jobs.getInstance().getJobManager().addPoint(p.getUniqueId(), POINTS_PER_PLANTED_SEED);
			}
		}
	}
	
	private boolean isFullyGrown(Block block) {
		MaterialData materialData = block.getState().getData();
		if (materialData instanceof Crops) {
			return (((Crops) materialData).getState() == CropState.RIPE);
		}
		else return false;
	}
	
	private boolean isHarvestableBlock(Material material) {
		return (material == Material.CROPS || material == Material.POTATO || material == Material.CARROT || material == Material.NETHER_WART_BLOCK);
	}
}
