package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.enchantment.Enchantment;

public class Enchant extends Command {

	@Override
	public String getAlias() {
		
		return "enchant";
	}

	@Override
	public String getDescription() {
		
		return "Enchants items to specified value (Creative Only)";
	}

	@Override
	public String getSyntax() {
		
		return ".enchant <level>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		if(mc.thePlayer.capabilities.isCreativeMode) 
		{
			if(args[0] == null) 
			{
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + getSyntax(), (int) 5, NotificationType.WARNING));
			} 
			else 
			{	
				for(Enchantment e : Hypnotic.instance.enchantmentManager.enchants) 
				{
					if(e == Enchantment.silkTouch) 
					{
						System.out.println("fuck you silk touch");
					}
					else 
					{
						mc.thePlayer.getHeldItem().addEnchantment(e, Integer.valueOf(args[0]));
					}
				}
				Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.white + "Successfully enchanted item to level " + args[0], (int) 5, NotificationType.INFO));
			}
		}
		else 
		{
			Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Creative only!", (int) 5, NotificationType.WARNING));
			
		}
	}
}
