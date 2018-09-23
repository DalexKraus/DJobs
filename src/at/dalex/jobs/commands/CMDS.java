package at.dalex.jobs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dalex.jobs.Jobs;
import at.dalex.jobs.type.EnumJob;

public class CMDS implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("§8-----=[§dJobs§8]=-----");
				p.sendMessage("§7Verfügbare Jobs:");
				p.sendMessage("§f- §8Bergarbeiter");
				p.sendMessage("§f- §eHolzfäller");
				p.sendMessage("§f- §2Botaniker");
				p.sendMessage("§f- §9Fischer");
				p.sendMessage("§f- §4Jäger");
				p.sendMessage("");
				p.sendMessage("§7Job beitreten: ");
				p.sendMessage("§8/§cJob §4join §7<Jobname>");
				p.sendMessage("");
				p.sendMessage("§7Job verlassen: ");
				p.sendMessage("§8/§cJob §4leave");
			}

			else if (args.length > 0) {
				if (args[0].equalsIgnoreCase("join")) {
					if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) == EnumJob.NONE) {
						if(args.length > 1) {
							if (args[1].equalsIgnoreCase("bergarbeiter")) {
								Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.MINER);
								p.sendMessage("§aDu bist dem Job §7Bergarbeiter §abeigetreten");
							}
							else if (args[1].equalsIgnoreCase("holzfäller")) {
								Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.WOODCUTTER);
								p.sendMessage("§aDu bist dem Job §eHolzfäller §abeigetreten");
							}
							else if (args[1].equalsIgnoreCase("botaniker")) {
								Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.FARMER);
								p.sendMessage("§aDu bist dem Job §2Botaniker §abeigetreten");
							}
							else if (args[1].equalsIgnoreCase("fischer")) {
								Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.FISHER);
								p.sendMessage("§aDu bist dem Job §9Fischer §abeigetreten");
							}
							else if (args[1].equalsIgnoreCase("jäger")) {
								Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.HUNTER);
								p.sendMessage("§aDu bist dem Job §4Jäger §abeigetreten");
							}

						} else p.sendMessage("§4ERROR: §cEs wurde ein ungültiger Job angegeben.");
					}
					else p.sendMessage("§4ERROR: §cDu hast bereits einen Job! §7(§8/job leave?§7)");
				}
				else if (args[0].equalsIgnoreCase("leave")) {
					if (Jobs.getInstance().getJobManager().getJob(p.getUniqueId()) != EnumJob.NONE) {
						p.sendMessage("§7Du hast deinen aktuellen job verlassen.");
						Jobs.getInstance().getJobManager().setJob(p.getUniqueId(), EnumJob.NONE);
					} else p.sendMessage("§cDu hast aktuell keinen Job!");
				}
			}
		} else sender.sendMessage("You have to be a player!");
		return true;
	}
}
