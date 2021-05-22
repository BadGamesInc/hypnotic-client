package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Toggle extends Command {

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "t";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Toggles modules";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".t (module)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null || Hypnotic.instance.moduleManager.getModuleByName(args[0]) == null) {
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		} else {
			Mod m = Hypnotic.instance.moduleManager.getModuleByName(args[0]);
			m.toggle();
			String state = m.isEnabled() ? ColorUtils.green + "enabled" : ColorUtils.red + "disabled";
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + m.getName() + " was " + state, (int) 5, NotificationType.INFO));
		}
		
	}

}
