package badgamesinc.hypnotic.module.misc;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.Killaura;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import net.minecraft.network.play.server.S02PacketChat;

public class KillSults extends Mod {
	
	public BooleanSetting messageSpam = new BooleanSetting("Message Spam", false);
	
	String allowedChars = "会意字會意字也馳驰施弓新隸體新隶体中國必亡漢字不滅天婦羅天麩羅亜米利加隸書隶书";
	
	public final int OUTPUT_STRING_LENGTH = 7;
	
	public String randomStuff;
    
	public KillSults() {
		super("KillSults", 0, Category.MISC, "Show those noobs why they should download hypnotic");
		addSettings(messageSpam);
	}
	
	@EventTarget
    public void onReceive(EventReceivePacket event){
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            
            String message = packet.getChatComponent().getUnformattedText();
            String[] killsults = 
            {
            	"You just got killed by Hypnotic ( https://github.com/BadGamesInc/hypnotic-client )", 
            	"Hypnotic is better than liquidbounce", 
            	"is bad, get good with Hypnotic", 
            	"Hypnotic is FREE and OPEN SOURCE", 
            	"Your shitty liquidbounce config cant save you from hypnotic client", 
            	"FDP? more like FPC",
            	"Tell me what other killsults I should add :)"
            };
            String[] strings = packet.getChatComponent().getUnformattedText().split(" ");    
            String victim = strings[0];
                      
            int randomIndex = ThreadLocalRandom.current().nextInt(0, killsults.length);

            if (message.contains("was killed by " + mc.thePlayer.getName()) || message.contains(mc.thePlayer.getName()) && message.contains("foi morto por " + mc.thePlayer.getName())) {
            	mc.thePlayer.sendChatMessage(victim + " " + killsults[randomIndex] + randomStuff);
            }
        }
    }
	
	@Override
	public void onUpdate() {
		Random random = new Random();
		
		randomStuff = " [" + getNextRandomString(allowedChars, random) + "]";
        if (messageSpam.isEnabled()) {
        	if (Killaura.target != null && Hypnotic.instance.moduleManager.getModule(Killaura.class).isEnabled()) {
        		mc.thePlayer.sendChatMessage("/tell " + Killaura.target.getName() + " NOOB" + randomStuff);
        		for (int i = 0; i < 1000; i ++) {
        			mc.thePlayer.sendChatMessage("/r " + Killaura.target.getName() + " NOOB EZ" + randomStuff);
        		}
        	}
        }
	}
	
	private String getNextRandomString(String allowedChars, Random random) {
        
        StringBuilder sbRandomString = new StringBuilder(OUTPUT_STRING_LENGTH);
        
        for(int i = 0 ; i < OUTPUT_STRING_LENGTH; i++){
            
            int randomInt = random.nextInt(allowedChars.length());

            sbRandomString.append( allowedChars.charAt(randomInt) );
        }
        
        return sbRandomString.toString();
        
    }


}
