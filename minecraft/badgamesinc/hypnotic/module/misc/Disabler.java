package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class Disabler extends Mod {
	
	private Packet packet;

	public Disabler() {
		super("Disabler", 0, Category.MISC);
		// TODO Auto-generated constructor stub
	}
	
	public void eventUpdate(EventUpdate event) {
		NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();
		if (packet instanceof C0FPacketConfirmTransaction)  event.isCancelled();
	}

	
}
