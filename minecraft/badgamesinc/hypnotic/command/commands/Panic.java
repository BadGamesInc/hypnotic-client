package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.enchantment.Enchantment;

public class Panic extends Command {

	@Override
	public String getAlias() {
		
		return "panic";
	}

	@Override
	public String getDescription() {
		
		return "Disables every module";
	}

	@Override
	public String getSyntax() {
		
		return ".panic";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		for(Mod m : Hypnotic.instance.moduleManager.modules) 
		{
			m.setEnabled(false);
		}
		Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "PANIC!", (int) 5, NotificationType.INFO));
	}
}
