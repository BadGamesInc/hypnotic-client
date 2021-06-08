package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;

public class Save extends Command {
  
  @Override
	public String getAlias() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "Saves your config";
	}

	@Override
	public String getSyntax() {
		return ".save";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
			Hypnotic.instance.cfgManager.saveAll();
			NotificationManager.getNotificationManager().createNotification(ColorUtils.white + "Successfully Saved", "", true, 1500, Type.INFO, Color.GREEN);
  	}
}
