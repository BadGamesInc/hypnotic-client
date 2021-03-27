package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class PizzaHutWY extends Mod
{
	public PizzaHutWY() 
	{
		super("Pizza Hut WY", 0, Category.MISC, "No one out pizzas the hut");
	}
	
	@Override
	public void onUpdate()
	{
		mc.thePlayer.sendChatMessage("307-324-7706 Pizza Hut WY");
	}
}