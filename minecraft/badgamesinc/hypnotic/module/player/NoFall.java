package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Mod {

	public Setting noFallMode;
	public NoFall() {
		super("NoFall", 0, Category.PLAYER, "Prevents fall damage");
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		Hypnotic.instance.setmgr.rSetting(noFallMode = new Setting("NoFall Mode", this, "Vanilla", options));
	}
	
	public void onUpdate() {
		this.setDisplayName("NoFall " + ColorUtils.white + "[" + noFallMode.getValString() + "] ");
		if(mc.thePlayer.fallDistance > 2)
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	}

}
