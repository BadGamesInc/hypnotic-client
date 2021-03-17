package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class AntiKnockback extends Mod {
	public static boolean knockback;
	public AntiKnockback() {
		super("AntiKnockback", 0, Category.COMBAT);		
	}
	
	public void onUpdate()
	{
	if(this.getState() == true)
	{
	knockback = true;
	}else
	{
	knockback = false;
	}
 
}

	private boolean getState() {
		// TODO Auto-generated method stub
		return false;
	}
}
