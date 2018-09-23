package at.dalex.jobs.type;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import at.dalex.jobs.Jobs;

public class JobHunter implements IJobType {

	private final int POINTS_PER_KILL = 4;
	
	public JobHunter() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
	}
	
	@EventHandler
	public void onKillEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			
			LivingEntity victim = (LivingEntity) e.getEntity();
			if (victim.getHealth() == 0) {
				
				if (e.getDamager() instanceof Player) {
					Player damager = (Player) e.getDamager();
					
					if (Jobs.getInstance().getJobManager().getJob(damager.getUniqueId()) == EnumJob.HUNTER) {
						
						Jobs.getInstance().getJobManager().addPoint(damager.getUniqueId(), POINTS_PER_KILL);
					}
				}
			}
		}
	}
}
