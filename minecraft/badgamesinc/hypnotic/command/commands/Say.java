package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
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
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		} else {
			mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
		}
		
	}

}
