package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventChat;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class RetardTalk extends Mod {

	public RetardTalk() {
		super("RetardTalk", 0, Category.MISC, "Make every message you send retarded");
	}
	
	@EventTarget
	public void onChat(EventChat e) {
		String m = e.getMessage();
		m = m.replaceAll("h", "H"); 
		m = m.replaceAll("e", "3"); 
		m = m.replaceAll("E", "3"); 
		m = m.replaceAll("hi", "KiLl yOuRsElF"); 
		m = m.replaceAll("Hi", "KiLl yOuRsElF");
		m = m.replaceAll("a", "A"); 
		m = m.replaceAll("i", "I"); 
		m = m.replaceAll("r", "R");
		m = m.replaceAll("o", "O"); 
		m = m.replaceAll("p", "P"); 
		m = m.replaceAll("s", "S");
		m = m.replaceAll("g", "G");
		m = m.replaceAll("u", "U");
		
		if(isValidMessage(m)) {
			e.setMessage(m);
		}	
	}
	
	public boolean isValidMessage(String message) {
		if(message.startsWith(".") || message.startsWith("/")) {
			return false;
		}
		return true;
	}

}
