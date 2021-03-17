package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Mod {

	public NoFall() {
		super("NoFall", 0, Category.PLAYER);
	}
	
	public void onUpdate() {
		if(mc.thePlayer.fallDistance > 2)
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	}

}
