package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.nbt.NBTTagCompound;

public class NBT extends Command {
	
	private NBTTagCompound toWrite = new NBTTagCompound();

	@Override
	public String getAlias() {
		
		return "nbt";
	}

	@Override
	public String getDescription() {
		
		return "Edit NBT data (Creative Only)";
	}

	@Override
	public String getSyntax() {
		
		return ".nbt <setname/clrname>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		if(mc.thePlayer.capabilities.isCreativeMode) 
		{
			if(args[0].equalsIgnoreCase("setname")) 
			{
				mc.thePlayer.getHeldItem().setStackDisplayName(args[1]);
			}
			else if(args[0].equalsIgnoreCase("clrname")) 
			{
				mc.thePlayer.getHeldItem().clearCustomName();
			}
		}
		else 
		{
			NotificationManager.getNotificationManager().createNotification(ColorUtils.red + "Creative only!", "", true, 1500, Type.WARNING, Color.RED);
		}
	}
}
