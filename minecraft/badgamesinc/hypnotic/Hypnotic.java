package badgamesinc.hypnotic;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.settings.SettingsManager;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.enchantment.EnchantmentManager;

public class Hypnotic {

	public static final Hypnotic instance = new Hypnotic();
	public SettingsManager setmgr;
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public HUD hud;
	public CommandManager commandManager;
	public NotificationManager notificationManager;
	public static FontManager fm;
	public SaveLoad saveload;
	public DiscordRP discordRP;
	public EnchantmentManager enchantmentManager;
	private Random r = new Random();
	
	public static final String clientName = "Hypnotic", clientVersion = "r1006", prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.white + ": ";
	
	public void startup() {	
		setmgr = new SettingsManager();
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		fm = new FontManager();
		commandManager = new CommandManager();
		notificationManager = new NotificationManager();
		hud = new HUD();
		saveload = new SaveLoad();
		discordRP = new DiscordRP();
		enchantmentManager = new EnchantmentManager();
		Display.setTitle(clientName + " " + clientVersion);
		eventManager.register(this);
		discordRP.start();
		discordRP.title = this.rptitle();
		
		//if(saveload.configs.exists()) {
			//saveload.load();
		//}
	}
	
	public void shutdown() {
		
		saveload.save();
		discordRP.shutdown();
	}
	
	public void onKeyPress(int key) {
		for(Mod m : moduleManager.getModules()) {
			if(m.getKey() == key) {
				m.toggle();
			}
			if (Minecraft.getMinecraft().currentScreen == null && key == Keyboard.KEY_PERIOD) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiChat());
			}
		}
	}
	
	public boolean onSendChatMessage(String s){
			if(s.startsWith(".")){
				commandManager.callCommand(s.substring(1));
			}
			return false;
		}
	
	public String rptitle() 
	{
		switch(r.nextInt(11)) 
		{
			case 0: return "Gaming PVP 1.8.8";
			case 1: return "I downloaded a PC pinger...";
			case 2: return "FUELPUMPS ON TOP :fuelpump:";
			case 3: return "don't let trent censor the media";
			case 4: return "Gaming.";
			case 5: return "https://github.com/BadGamesInc/hypnotic-client/";
			case 6: return "Down with the Millers!";
			case 7: return "Hypnotic on top";
			case 8: return "L";
			case 9: return "E4PE4J";
			case 10: return "Now featuring bypasses!";
			default: return "Default Text";
		}
	}
	
	
	
}
