package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Friend extends Command {

	@Override
	public String getAlias() {
		return "friend";
	}

	@Override
	public String getDescription() {
		return "Add people as friends";
	}

	@Override
	public String getSyntax() {
		return ".friend (add/remove) (name), .friend (list)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].equalsIgnoreCase("add")) {
			
			//if (!Hypnotic.instance.friendManager.isFriend(args[1])) {
				Hypnotic.instance.friendManager.addFriend(args[1]);
				NotificationManager.getNotificationManager().createNotification("Added " + ColorUtils.green + args[1] + ColorUtils.white + " to your friends list", "", true, 1500, Type.INFO, Color.GREEN);
				Hypnotic.instance.saveload.save();
				//} else if (Hypnotic.instance.friendManager.isFriend(args[1])) {
				//NotificationManager.getNotificationManager().createNotification(ColorUtils.green + args[1] + ColorUtils.white + " is already on your friends list!", "", true, (int) 5, Type.WARNING, Color.RED);
			//}
		} else if (args[0].equalsIgnoreCase("remove")) {
			Hypnotic.instance.friendManager.friends.remove(args[1]);
			Hypnotic.instance.saveload.save();
			NotificationManager.getNotificationManager().createNotification("Removed " + ColorUtils.red + args[1] + ColorUtils.white + " from your friends list", "", true, 1500, Type.INFO, Color.GREEN);
		} else if (args[0].equalsIgnoreCase("list")) {
			if (Hypnotic.instance.friendManager.getFriends().size() != 0) {
				Wrapper.rawTellPlayer("§n§lFriends: " + "(" + Hypnotic.instance.friendManager.getFriends().size() + ")");
				Wrapper.rawTellPlayer("");
				for (String friend : Hypnotic.instance.friendManager.getFriends()) {
					Wrapper.rawTellPlayer(ColorUtils.green + friend);
				}
			} else {
				Wrapper.rawTellPlayer("§c§l§oYou have no friends...");
			}
		} else if (args[0] == null || args[1] == null)
			NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
		
	}

}
