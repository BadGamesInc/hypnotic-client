package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
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
			Wrapper.tellPlayer("Only for creative mode.");
		}
	}
}
