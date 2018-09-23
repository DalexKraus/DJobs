package at.dalex.jobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import at.dalex.jobs.commands.CMDS;
import at.dalex.jobs.config.Config;
import at.dalex.jobs.manager.JobManager;

public class Jobs extends JavaPlugin {

	public static String prefix = "§8[§dJobs§8]";
	
	private static Jobs instance;
	private JobManager jobManager;
	
	private Config config;
	
	@Override
	public void onEnable() {
		instance = this;
		
		jobManager = new JobManager();
		
		config = new Config();
		config.load();
		
		getCommand("jobs").setExecutor(new CMDS());
		getServer().getPluginManager().registerEvents(jobManager, this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					player.sendMessage("§9Es ist Zahltag! §7Jeder Spieler bekommt nun sein Gehalt ausgezahlt ...");
				}
				jobManager.performPayday();
			}
			
		}, 10 * 60 * 20L, 10 * 60 * 20L);
	}
	
	@Override
	public void onDisable() {
		config.save();
	}

	public JobManager getJobManager() {
		return this.jobManager;
	}
	
	public static Jobs getInstance() {
		return instance;
	}
}
