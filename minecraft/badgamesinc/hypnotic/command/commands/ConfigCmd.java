package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
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
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		}
		
		if (args[0].equalsIgnoreCase("load")) {
			Hypnotic.cfgManager.load(configName);
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Loaded " + configName, (int) 5, NotificationType.INFO));
		} else if (args[0].equalsIgnoreCase("save")) {
			Hypnotic.cfgManager.save(configName);
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Current config has been saved as " + configName, (int) 5, NotificationType.INFO));
		} else if (args[0].equalsIgnoreCase("delete")) {
			Hypnotic.cfgManager.delete(configName);
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Deleted " + configName, (int) 5, NotificationType.INFO));
		} else if (args[0].equalsIgnoreCase("list")) {
			Wrapper.tellPlayer("Configs: ");
			Hypnotic.cfgManager.list();
		}
  	}
}
