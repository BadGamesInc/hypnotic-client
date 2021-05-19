package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.server.S02PacketChat;

public class RetardDetector extends Mod {

	public RetardDetector() {
		super("Retard Detector", 0, Category.MISC, "Find retards in the chat");
	}
	
	@EventTarget
    public void onReceive(EventReceivePacket event){
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            
            String message = packet.getChatComponent().getUnformattedText();
            String[] strings = packet.getChatComponent().getUnformattedText().split(" ");    
            String retard = strings[0];

            if (message.contains("Try FDP") || message.contains("try fdp") || message.contains("liquidbounce") || message.contains("Liquidbounce")) {
            	mc.thePlayer.sendChatMessage("[HYPNOTIC RETARD DETECTION] " + retard + " is retarded");
            }
        }
    }

}
