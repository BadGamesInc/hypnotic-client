package badgamesinc.hypnotic.module.misc;

import java.util.Random;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.Session;

public class AutoL extends Mod {

	public AutoL() {
		super("AutoL", 0, Category.MISC, "Automatically say L when you win");
	}

	@EventTarget
    public void onReceive(EventReceivePacket event){
        if(event.getPacket() instanceof S02PacketChat){
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();

            if (message.contains("BAN " + mc.thePlayer.getName())) {
            	Random r = new Random();
            	String randomUsername = "RandomName" + r.nextInt(9999);
            	mc.session = new Session(randomUsername, "", "", "mojang");
            }
            if(message.contains(mc.thePlayer.getName()) && message.contains("venceu a partida") || message.contains("You won the fight!") || message.contains("You won! Want to play again?")){
            	mc.thePlayer.sendChatMessage("L you guys got rekt by Hypnotic");;
            }
        }
    }

}
