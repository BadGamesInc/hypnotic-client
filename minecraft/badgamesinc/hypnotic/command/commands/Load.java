package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Load extends Command {
  
  @Override
	public String getAlias() {
		return "load";
	}

	@Override
	public String getDescription() {
		return "Loads your config";
	}

	@Override
	public String getSyntax() {
		return ".load";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
			Hypnotic.instance.saveload.load();;
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Successfully Loaded", (int) 5, NotificationType.INFO));
  	}
}
