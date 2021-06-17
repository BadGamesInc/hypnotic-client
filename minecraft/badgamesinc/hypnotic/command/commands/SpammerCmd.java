package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.misc.ChatSpammer;

public class SpammerCmd extends Command {

	@Override
	public String getAlias() {
		return "spammer";
	}

	@Override
	public String getDescription() {
		return "Sets the chat spammer messages";
	}

	@Override
	public String getSyntax() {
		return ".spammer (message/list)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		ChatSpammer spammer = Hypnotic.instance.moduleManager.chatSpammer;
		String message = String.join(" ", args);
		spammer.custom.add(message);
		NotificationManager.getNotificationManager().createNotification("Spammer", "Added message", true, 1000, Type.INFO, Color.WHITE);
	}

}
