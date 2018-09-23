package at.dalex.jobs.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import at.dalex.bank.Bank;
import at.dalex.bank.Main;
import at.dalex.jobs.type.EnumJob;
import at.dalex.jobs.type.IJobType;
import at.dalex.jobs.type.JobFarmer;
import at.dalex.jobs.type.JobFisher;
import at.dalex.jobs.type.JobHunter;
import at.dalex.jobs.type.JobMiner;
import at.dalex.jobs.type.JobTreeFeller;

public class JobManager implements Listener {

	private static ArrayList<IJobType> jobs = new ArrayList<IJobType>();
	private HashMap<UUID, EnumJob> activeJobs = new HashMap<>();
	private HashMap<UUID, Integer> points = new HashMap<UUID, Integer>();
	
	static {
		jobs.add(new JobMiner());
		jobs.add(new JobHunter());
		jobs.add(new JobFarmer());
		jobs.add(new JobFisher());
		jobs.add(new JobTreeFeller());
	}
	
	public void setJob(UUID player, EnumJob job) {
		activeJobs.put(player, job);
	}
	
	public EnumJob getJob(UUID player) {
		if (activeJobs.containsKey(player)) return activeJobs.get(player);
		return EnumJob.NONE;
	}
	
	public void addPoint(UUID player, int amount) {
		int currentPoints = getPoints(player);
		points.put(player, currentPoints + 1);
	}
	
	public void performPayday() {
		for (UUID player : points.keySet()) {
			payout(player);
		}
	}
	
	public void payout(UUID player) {
		int pointAmount = getPoints(player);
		points.put(player, 0);
		Bank.addBalance(player, pointAmount);
		Player p = Bukkit.getServer().getPlayer(player);
		if (p.isOnline()) {
			if (pointAmount > 0)
				p.sendMessage("§eDurch das Ausüben deines Jobs wurden §6" + pointAmount + "§5" + Main.CURRENCY + "§e auf dein Bankkonto überwiesen.");
			else p.sendMessage("§8Du hast keine Arbeit verübt.");
		}
	}
	
	private int getPoints(UUID player) {
		if (points.containsKey(player)) {
			return points.get(player);
		}
		return 0;
	}
	
	public HashMap<UUID, EnumJob> getActiveJobs() {
		return this.activeJobs;
	}
}
