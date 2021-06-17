package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;
import java.util.Random;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.TimerUtils;

public class ChatSpammer extends Mod {

	public ArrayList<String> defaultMessages;
	public ArrayList<String> custom;
	public TimerUtils timer = new TimerUtils();
	
	public ModeSetting mode = new ModeSetting("Mode", "Default", "Default", "Custom");
	public NumberSetting delay = new NumberSetting("Delay", 4, 0, 30, 1);
	
	public ChatSpammer() {
		super("Spammer", 0, Category.MISC, "Spam that chat");
		
		defaultMessages = new ArrayList<String>();
		custom = new ArrayList<String>();
		timer = new TimerUtils();
		defaultMessages.add("Imagine not using " + Hypnotic.clientName);
		defaultMessages.add("L");
		defaultMessages.add("Bad server");
		defaultMessages.add("307-324-7706 Pizza Hut WY");
		defaultMessages.add("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		defaultMessages.add("E FOR POWERMAC E FOR JUSTICE");
		defaultMessages.add("FUELPUMPS ON TOP");
		defaultMessages.add("E");
		defaultMessages.add("when the impostor is sus");
		defaultMessages.add("This is Mr. Hood from the Sherwood Law Group. We've been suing the rich and giving to the poor since 1944. We can solve all of your merry little problems! See what we're about below! https://youtube.com/watch?v=cOOG3bRujoM");
		defaultMessages.add("bro its a pc pinger, dont download it");
		addSettings(mode, delay);
	}
	
	public double getDelayValue() {
		return delay.getValue(); 	
	}
	
	public void onUpdate() {
		if(timer.hasTimeElapsed(getDelayValue() * 1000, true)) {
			Random r = new Random();
			String message = defaultMessages.get(index);
			int index = r.nextInt(defaultMessages.size());
			if (mode.is("Default")) {
				index = r.nextInt(defaultMessages.size());
				message = defaultMessages.get(index);
			} else if (custom.size() > 0 && mode.is("Custom")) {
				index = r.nextInt(custom.size());
				message = custom.get(index);
			}
			
			mc.thePlayer.sendChatMessage(message);
			
			timer.reset();
		}
	}
}
