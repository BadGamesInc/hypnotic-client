package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Blink extends Mod {

	public Blink() {
		super("Blink", 0, Category.MISC, "Stops you from sending packets (visualy teleports you)");
	}
	
	@EventTarget
	public void receivePacket(EventReceivePacket e) {
		if (mc.theWorld != null)
			e.setCancelled(true);
		else
			e.setCancelled(false);
	}

}
