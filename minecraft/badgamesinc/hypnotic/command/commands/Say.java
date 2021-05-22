package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command {

	@Override
	public String getAlias() {
		
		return "say";
	}

	@Override
	public String getDescription() {
		
		return "Says the message you type in chat";
	}

	@Override
	public String getSyntax() {
		
		return ".say (message)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
		} else {
			mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
		}
		
	}

}
