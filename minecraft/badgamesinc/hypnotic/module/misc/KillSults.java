package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.events.EventChat;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class KillSults extends Mod {

	public KillSults() {
		super("KillSults", 0, Category.MISC, "Show those noobs why they should download hypnotic");
	}
	
	public void eventChat(EventChat e) {
		String m = e.getMessage();	
		
		if(m.contains(mc.thePlayer.getName()) && m.contains("foi morto por") || m.contains("e")) {
			mc.thePlayer.sendChatMessage("You got fucked by hypnotic");
		}
		
	}

}
