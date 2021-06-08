package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;

public class Panic extends Command {

	@Override
	public String getAlias() {
		
		return "panic";
	}

	@Override
	public String getDescription() {
		
		return "Disables every module";
	}

	@Override
	public String getSyntax() {
		
		return ".panic";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		for(Mod m : Hypnotic.instance.moduleManager.modules) 
		{
			m.setEnabled(false);
		}
		NotificationManager.getNotificationManager().createNotification(ColorUtils.red + "PANIC" + getSyntax(), "", true, 1500, Type.WARNING, Color.RED);
	}
}
