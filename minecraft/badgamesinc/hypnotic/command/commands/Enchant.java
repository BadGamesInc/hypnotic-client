package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
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
				NotificationManager.getNotificationManager().createNotification("Invalid Usage!", "Usage: " + getSyntax(), true, 2500, Type.WARNING, Color.RED);
			} 
			else 
			{	
				for(Enchantment e : Hypnotic.instance.enchantmentManager.enchants) 
				{
					if(e == Enchantment.silkTouch) 
					{
						System.out.println("frick you silk touch");
					}
					else 
					{
						mc.thePlayer.getHeldItem().addEnchantment(e, Integer.valueOf(args[0]));
					}
				}
				NotificationManager.getNotificationManager().createNotification(ColorUtils.white + "Successfully enchanted item to level " + args[0], "", true, 1500, Type.INFO, Color.GREEN);
			}
		}
		else 
		{
			NotificationManager.getNotificationManager().createNotification(ColorUtils.red + "Creative only!", "", true, 1500, Type.WARNING, Color.RED);
			
		}
	}
}
