package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;

public class LogoName extends Command {

	@Override
	public String getAlias() {
		return "logo";
	}

	@Override
	public String getDescription() {
		return "Sets the client logo name";
	}

	@Override
	public String getSyntax() {
		return ".logo (name)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		
		if (args[0] == null) {
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		}
		
		String name = Character.toUpperCase(args[0].charAt(0)) + ColorUtils.white + args[0].toLowerCase().substring(1);
		Hypnotic.instance.hud.logoName = name;
		
	}

}
