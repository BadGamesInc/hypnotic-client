package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.enchantment.Enchantment;

public class Enchant extends Command {

	@Override
	public String getAlias() {
		
		return "enchant";
	}

	@Override
	public String getDescription() {
		
		return "Enchants item to 32k (Creative Only, WIP)";
	}

	@Override
	public String getSyntax() {
		
		return ".enchant";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		//if(mc.thePlayer.capabilities.isCreativeMode) 
		//{
			mc.thePlayer.getHeldItem().addEnchantment(Enchantment.sharpness, (short) 32767);
			Wrapper.tellPlayer("Successfully enchanted item");
		//}
		//else 
		//{
			//Wrapper.tellPlayer("Only for creative mode.");
		//}
	}
}
