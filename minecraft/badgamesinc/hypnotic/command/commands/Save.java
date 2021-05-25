package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

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
			Hypnotic.instance.cfgManager.saveAll();;
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Successfully Saved", (int) 5, NotificationType.INFO));
  	}
}
