package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
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
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
    	options.add("Test");
    	options.add("Test1");
    	Hypnotic.instance.setmgr.rSetting(new Setting("Test", this, "Test", options));
	}
	
	public void eventUpdate(EventUpdate event) {
		NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();
		if (packet instanceof C0FPacketConfirmTransaction)  event.isCancelled();
	}

	
}
