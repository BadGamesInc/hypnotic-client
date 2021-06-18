package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;

public class Toggle extends Command {

	@Override
	public String getAlias() {
		return "t";
	}

	@Override
	public String getDescription() {
		return "Toggles modules";
	}

	@Override
	public String getSyntax() {
		return ".t (module)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		String mod = String.join(" ", args);
		if(args == null || Hypnotic.instance.moduleManager.getModuleByName(mod) == null) {
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		} else {
			Mod m = Hypnotic.instance.moduleManager.getModuleByName(mod);
			m.toggle();
		}
		
	}

}
