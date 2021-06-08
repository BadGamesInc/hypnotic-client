package badgamesinc.hypnotic.command.commands;
import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.SoundUtil;
import badgamesinc.hypnotic.util.Wrapper;

public class Bind extends Command {

	@Override
	public String getAlias() 
	{
		return "bind";
	}

	@Override
	public String getDescription() 
	{
		return "Binds modules to a specified key";
	}

	@Override
	public String getSyntax() 
	{
		return ".bind (key) (module)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		SoundUtil.playSound("on.wav");
		if (args.length == 2) {
				String moduleName = args[0];
				String keyName = args[1];

			for(Mod m : Hypnotic.instance.instance.moduleManager.modules) {
				m.getName().replaceAll(" ", "");
				if(m.getName().equalsIgnoreCase(moduleName)) {
					if (args[1].length() == 1) {
						m.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
	
						NotificationManager.getNotificationManager().createNotification(String.format("Bound %s to %s", ColorUtils.white + m.getName() + ColorUtils.gray, ColorUtils.white + Keyboard.getKeyName(m.getKey())) + "     ", "", true, 1500, Type.INFO, Color.GREEN);
						if (args[1].length() != 1) {
						NotificationManager.getNotificationManager().createNotification(String.format(args[1]), "", true, 1500, Type.INFO, Color.GREEN);
						
						}
					} else if (args[1].equalsIgnoreCase("none")){
						m.setKey(Keyboard.KEY_NONE);
						NotificationManager.getNotificationManager().createNotification("Unbound " + m.getName(), "", true, 1500, Type.INFO, Color.GREEN);
					}
				}
			}
		}

		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				for(Mod m : Hypnotic.instance.moduleManager.modules) {
					m.setKey(0);
				}
				Wrapper.tellPlayer("Cleared all binds");
			} else 
				Wrapper.tellPlayer(args[0] + " is not a module");
		}

		if(args[0] == null) {
			Wrapper.tellPlayer("Usage: " + getSyntax());
		}

	}

}
