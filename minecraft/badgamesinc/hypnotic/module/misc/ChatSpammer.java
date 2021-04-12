package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;
import java.util.Random;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimerUtils;

public class ChatSpammer extends Mod {

	ArrayList<String> messages;
	public TimerUtils timer = new TimerUtils();
	
	public ChatSpammer() {
		super("ChatSpammer", 0, Category.MISC, "Spam that chat");
		
		messages = new ArrayList<String>();
		timer = new TimerUtils();
		messages.add("Imagine not using " + Hypnotic.clientName);
		messages.add("L");
		messages.add("Bad server");
		messages.add("307-324-7706 Pizza Hut WY");
		messages.add("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		messages.add("E FOR POWERMAC E FOR JUSTICE");
		messages.add("FUELPUMPS ON TOP");
		messages.add("E");
		messages.add("when the impostor is sus");
		messages.add("This is Mr. Hood from the Sherwood Law Group. We've been suing the rich and giving to the poor since 1944. We can solve all of your merry little problems! See what we're about below! https://youtube.com/watch?v=cOOG3bRujoM");
		messages.add("bro its a pc pinger, dont download it");
	}
	
	public void setup()
	{
		Hypnotic.instance.setmgr.rSetting(new Setting("Delay", this, 4, 0, 30, false)); 
	}
	
	public double getDelayValue() {
		return Hypnotic.instance.setmgr.getSettingByName("Delay").getValDouble(); 	
	}
	
	public void onUpdate() {
		if(timer.hasTimeElapsed(getDelayValue() * 1000, true)) {
			Random r = new Random();
			int index = r.nextInt(messages.size());
			String message = messages.get(index);
			
			mc.thePlayer.sendChatMessage(message);
			
			timer.reset();
		}
	}
}
