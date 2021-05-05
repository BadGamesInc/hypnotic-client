package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.network.play.server.S02PacketChat;

public class KillSults extends Mod {
	
	public Setting mode;
	   
	public KillSults() {
		super("KillSults", 0, Category.MISC, "Show those noobs why they should download hypnotic");
	}
	
	@EventTarget
    public void onReceive(EventReceivePacket event){
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            
            String message = packet.getChatComponent().getUnformattedText();
            String randomStuff = " [" + ThreadLocalRandom.current().nextInt(10000, 99999) + "]";
            String[] killsults = {"You just got killed by Hypnotic ( https://github.com/BadGamesInc/hypnotic-client )", "Hypnotic is better than liquidbounce", "Hypnotic is FREE and OPEN SOURCE", "Tell me what other killsults I should add :)"};
            String[] strings = packet.getChatComponent().getUnformattedText().split(" ");    
            String victim = strings[1];
            
            int randomIndex = ThreadLocalRandom.current().nextInt(0, killsults.length);

            if(message.contains(mc.thePlayer.getName()) && message.contains("foi morto por " + mc.thePlayer.getName())){
            	mc.thePlayer.sendChatMessage(victim + " " + killsults[randomIndex] + randomStuff);
            }
        }
    }


}
