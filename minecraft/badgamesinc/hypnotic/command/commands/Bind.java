package badgamesinc.hypnotic.command.commands;
import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
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
	public void onCommand(String command, String[] args) throws Exception 
	{
		if(args[0].equalsIgnoreCase("clear")) 
		{
			if(args[1].equalsIgnoreCase("all")) 
			{
				for(Mod m : Hypnotic.instance.moduleManager.modules) 
				{
					m.setKey(0);
				}
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Unbound all modules", (int) 5, NotificationType.INFO));
			}
			else if(!args[1].equalsIgnoreCase("all"))
			{
				Hypnotic.instance.moduleManager.getModuleByName(args[1]).setKey(0);
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Unbound " + ColorUtils.gray + args[1], (int) 5, NotificationType.INFO));
			}
		}
		else 
		{
			Hypnotic.instance.moduleManager.getModuleByName(args[1]).setKey(Keyboard.getKeyIndex(args[0].toUpperCase()));
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Bound " + ColorUtils.gray + args[1] + ColorUtils.white + " to " + ColorUtils.gray + args[0], (int) 5, NotificationType.INFO));
		}
	}

}
