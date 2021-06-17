package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.server.S02PacketChat;

public class StaffAlert extends Mod {

	public StaffAlert() {
		super("StaffAler", 0, Category.MISC, "Notifies you if staff enters your game");
	}

	String[] staff = new String[] {"Fake_End", "dosd", "MODERATOR", "ADMIN"};
	
	@EventTarget
	public void receiveChat(EventReceivePacket e) {
		if (e.getPacket() instanceof S02PacketChat) {
			S02PacketChat packet = (S02PacketChat) e.getPacket();
			String message = packet.getChatComponent().getUnformattedText();
			if (message.contains(staff.toString())) {
				
			}
		}
	}
}
