package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class ConfigCmd extends Command {
  
  @Override
	public String getAlias() {
		return "config";
	}

	@Override
	public String getDescription() {
		return "Loads/saves your config";
	}

	@Override
	public String getSyntax() {
		return ".config (load/save/delete) (name), .config list";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		String configName = "";
		if (args.length >= 2) {
			configName = args[1];
		}
		
		if (args[0] == null) {
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		}
		
		if (args[0].equalsIgnoreCase("load")) {
			Hypnotic.cfgManager.load(configName);
			NotificationManager.getNotificationManager().createNotification(ColorUtils.white + "Loaded " + configName, "", true, 1500, Type.INFO, Color.GREEN);
		} else if (args[0].equalsIgnoreCase("save")) {
			Hypnotic.cfgManager.save(configName);
			NotificationManager.getNotificationManager().createNotification(ColorUtils.white + "Current config has been saved as " + configName, "", true, 1500, Type.INFO, Color.GREEN);
		} else if (args[0].equalsIgnoreCase("delete")) {
			Hypnotic.cfgManager.delete(configName);
			NotificationManager.getNotificationManager().createNotification(ColorUtils.white + "Deleted " + configName, "", true, 1500, Type.INFO, Color.GREEN);
		} else if (args[0].equalsIgnoreCase("list")) {
			Wrapper.tellPlayer("Configs: ");
			Hypnotic.cfgManager.list();
		}
  	}
}
