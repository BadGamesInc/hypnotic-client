package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Teleport extends Command {

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "tp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Its like /tp but you don't need op";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".tp (x, y, z)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		} else {
			mc.thePlayer.setPosition(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "TP'd to " + args[0] + ", " + args[1] + ", " + args[2], (int) 5, NotificationType.INFO));
		}
		
	}

}
