package at.dalex.jobs.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.dalex.jobs.Jobs;
import at.dalex.jobs.type.EnumJob;

public class Config {

	private File file;
	private FileConfiguration config;
	
	public Config() {
		file = new File("plugins/DJobs/config.yml");
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void load() {
		
		if (config.get("users") != null) {
			for (String rawId : config.getConfigurationSection("users").getKeys(false)) {
				UUID uuid = UUID.fromString(rawId);
				
				if (config.get("users." + rawId + ".job") != null) {
					EnumJob job = EnumJob.valueOf(config.getString("users." + rawId + ".job"));
					Jobs.getInstance().getJobManager().setJob(uuid, job);
				}
			}
		}
	}
	
	public void save() {
		
		for (UUID uuid : Jobs.getInstance().getJobManager().getActiveJobs().keySet()) {
			EnumJob job = Jobs.getInstance().getJobManager().getJob(uuid);
			config.set("users." + uuid.toString() + ".job", job.toString());
		}
		
		saveConfigFile();
	}
	
	private void saveConfigFile() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
