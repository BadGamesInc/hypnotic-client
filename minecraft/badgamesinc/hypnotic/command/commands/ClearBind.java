package badgamesinc.hypnotic.command.commands;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class ClearBind extends Command {

	@Override
	public String getAlias() 
	{
		return "clearbind";
	}

	@Override
	public String getDescription() 
	{
		return "Clears binds";
	}

	@Override
	public String getSyntax() 
	{
		return ".clearbind (module/all)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
			if(args[0].equalsIgnoreCase("all")) 
			{
				for(Mod m : Hypnotic.instance.moduleManager.modules) 
				{
					m.setKey(0);
				}
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Unbound all modules", (int) 5, NotificationType.INFO));
			}
			else
			{
				Hypnotic.instance.moduleManager.getModuleByName(args[0]).setKey(0);
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Unbound " + ColorUtils.gray + args[0], (int) 5, NotificationType.INFO));
			}
	}

}
