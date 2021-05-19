package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
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
				Wrapper.tellPlayer("Usage: " + getSyntax());
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
				Wrapper.tellPlayer("Successfully enchanted item to level " + args[0]);
			}
		}
		else 
		{
			Wrapper.tellPlayer("Only for creative mode.");
		}
	}
}
