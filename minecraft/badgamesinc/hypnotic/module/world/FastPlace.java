package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class FastPlace extends Mod {

	public FastPlace() {
		super("FastPlace", 0, Category.WORLD);
		
	}
	
	@Override
	public void onUpdate() {
		mc.rightClickDelayTimer = 0;
	}
	
	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 6;
	}
}
