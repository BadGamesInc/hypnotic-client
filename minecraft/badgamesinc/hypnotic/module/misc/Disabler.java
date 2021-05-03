package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class Disabler extends Mod {
	
	private Packet packet;

	public Disabler() {
		super("Disabler", 0, Category.MISC, "Attempts to cancel the client sending packets to the server");
	}
	
	ArrayList<Packet> packets = new ArrayList<>();
    @EventTarget
    public void onReceivePacket(EventReceivePacket event){

    }

    @EventTarget
    public void onSendPacket(EventSendPacket eventSendPacket){
    	//eventSendPacket.setCancelled(true);
    }

    @Override
    public void onDisable(){

    }
}
