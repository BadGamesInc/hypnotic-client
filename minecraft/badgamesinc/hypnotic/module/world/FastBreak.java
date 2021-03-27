package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FastBreak extends Mod {

	public FastBreak() {
		super("FastBreak", 0, Category.WORLD, "Break blocks faster");

	}
	
	@Override
	public void onUpdate() {
		mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1));
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.removePotionEffect(Potion.digSpeed.id);
	}
}
