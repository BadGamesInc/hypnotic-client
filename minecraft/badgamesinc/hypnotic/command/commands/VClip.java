package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class VClip extends Command {

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "vclip";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Clips you a specified number of blocks vertically";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".vclip (amount)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		} else {
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Integer.valueOf(args[0]), mc.thePlayer.posZ);
			Wrapper.tellPlayer("Clipped " + args[0] + " blocks vertically");
		}
		
	}

}
