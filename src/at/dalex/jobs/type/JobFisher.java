package at.dalex.jobs.type;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import at.dalex.jobs.Jobs;

public class JobFisher implements IJobType {

	private final int POINTS_PER_FISH = 2;
	
	public JobFisher() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		
		Player p = e.getPlayer();
		
		if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) == EnumJob.FISHER) {
			
			if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
				
				Jobs.getInstance().getJobManager().addPoint(p.getUniqueId(), POINTS_PER_FISH);
			}
		}
	}
}
