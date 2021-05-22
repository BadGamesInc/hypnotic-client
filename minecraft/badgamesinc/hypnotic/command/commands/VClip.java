package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
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
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		} else {
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Integer.valueOf(args[0]), mc.thePlayer.posZ);
			Wrapper.tellPlayer("Clipped " + args[0] + " blocks vertically");
		}
		
	}

}
