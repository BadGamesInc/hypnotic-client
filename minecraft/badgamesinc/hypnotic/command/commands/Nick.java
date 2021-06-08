package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;

public class Nick extends Command {

	public static String name;
	
	@Override
	public String getAlias() {
		return "nick";
	}

	@Override
	public String getDescription() {
		return "Change your name client-side";
	}

	@Override
	public String getSyntax() {
		return ".nick (name)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0] != null) {
			this.name = args[0];
			this.name = this.name.replace("&", "\247");
			this.name = this.name.replace("\\247", "\247");
			this.name = this.name.replace("_", " ");
			NotificationManager.getNotificationManager().createNotification("Nicked as " + ColorUtils.green + args[0], "", true, 1500, Type.INFO, Color.GREEN);
		}
	}

}
