package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Seed extends Command {

	@Override
	public String getAlias() {
		
		return "seed";
	}

	@Override
	public String getDescription() {
		
		return "The seed of the current world";
	}

	@Override
	public String getSyntax() {
		
		return ".seed";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		Wrapper.tellPlayer("Seed: " + mc.theWorld.getSeed());
	}
}
