package at.dalex.jobs.type;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import at.dalex.jobs.Jobs;

public class JobTreeFeller implements IJobType {

	private final int POINTS_PER_LOG = 1;
	
	public JobTreeFeller() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {

		Player p = e.getPlayer();
		if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) == EnumJob.WOODCUTTER) {
			if (e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2) {

				Jobs.getInstance().getJobManager().addPoint(p.getUniqueId(), POINTS_PER_LOG);
			}
		}
	}
}
