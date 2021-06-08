package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Mod {

	public ModeSetting noFallMode = new ModeSetting("NoFall Mode", "Vanilla", "Vanilla", "NCP");
	int ncpWait = 100;
	
	public NoFall() {
		super("NoFall", 0, Category.PLAYER, "Prevents fall damage");
		addSettings(noFallMode);
	}
	
	public void onUpdate() {
		this.setDisplayName("NoFall " + ColorUtils.white + "[" + noFallMode.getSelected() + "] ");
		if (noFallMode.is("Vanilla")) {
			if(mc.thePlayer.fallDistance > 2)
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
	}
}
