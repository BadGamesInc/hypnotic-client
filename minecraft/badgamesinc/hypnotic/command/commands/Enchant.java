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
		
		return "Enchants item to 32k (Creative Only)";
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
				Wrapper.tellPlayer("Usage: " + getSyntax());
			} 
			else 
			{
				// swords
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.sharpness, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.looting, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.smite, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.baneOfArthropods, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.fireAspect, Integer.valueOf(args[0]));
				
				// armor
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.protection, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.fireProtection, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.projectileProtection, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.blastProtection, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.thorns, Integer.valueOf(args[0]));
				
				// helmet
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.aquaAffinity, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.respiration, Integer.valueOf(args[0]));
				
				// boots
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.featherFalling, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.depthStrider, Integer.valueOf(args[0]));
				
				// tools
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.fortune, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.efficiency, Integer.valueOf(args[0]));
				//mc.thePlayer.getHeldItem().addEnchantment(Enchantment.silkTouch, Integer.valueOf(args[0]));
				
				// bows
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.flame, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.power, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.punch, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.infinity, Integer.valueOf(args[0]));
				
				// fishing rods
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.luckOfTheSea, Integer.valueOf(args[0]));
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.lure, Integer.valueOf(args[0]));
				
				// generic
				mc.thePlayer.getHeldItem().addEnchantment(Enchantment.unbreaking, Integer.valueOf(args[0]));
				
				Wrapper.tellPlayer("Successfully enchanted item to level " + args[0]);
			}
			
		}
		else 
		{
			Wrapper.tellPlayer("Only for creative mode.");
		}
	}
}
