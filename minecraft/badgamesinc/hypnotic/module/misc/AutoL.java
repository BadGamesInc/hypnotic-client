package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoL extends Mod {

	public AutoL() {
		super("AutoL", 0, Category.MISC, "Automatically say L when you win");
	}

	
	/*@EventTarget
	public void eventReceivePacket(EventChat e) {
		String message = e.getMessage();		
					
		if(message.contains("venceu a partida") && message.contains(mc.thePlayer.getName()) || message.contains("You won the fight!") || message.contains("venceu a partida!") && message.contains(mc.thePlayer.getName())) {
			mc.thePlayer.sendChatMessage("L");
		}
			
		
	}
	*/
	@EventTarget
    public void onReceive(EventReceivePacket event){
        if(event.getPacket() instanceof S02PacketChat){
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();

            if(message.contains(mc.thePlayer.getName()) && message.contains("venceu a partida") || message.contains("You won the fight!")){
            	mc.thePlayer.sendChatMessage("L you guys got rekt by Hypnotic");;
            }
        }
    }

}
