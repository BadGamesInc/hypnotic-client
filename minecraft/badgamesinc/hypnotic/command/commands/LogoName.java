package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;

public class LogoName extends Command {

	public static String name = "";
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
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		}
				
		switch(Hypnotic.instance.moduleManager.logo.textType.getSelected()) {
		
			case "Single Letter Color":
				name = Character.toString(args[0].charAt(0)) + ColorUtils.white + args[0].substring(1);
				break;
				
			case "Full Text Color":
				name = args[0];
				break;
		}
		Hypnotic.instance.hud.logoName = name;
		
	}

}
