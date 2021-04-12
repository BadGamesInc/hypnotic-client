package badgamesinc.hypnotic;

import java.util.Random;

import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.gui.clickgui.ClickGUI;
import badgamesinc.hypnotic.gui.clickgui.settings.SettingsManager;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class Hypnotic {

	public static final Hypnotic instance = new Hypnotic();
	public SettingsManager setmgr;
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public ClickGUI clickgui;
	public HUD hud;
	public CommandManager commandManager;
	public static FontManager fm;
	public SaveLoad saveload;
	private DiscordRP discordRP = new DiscordRP();
	private Random r = new Random();
	public static final String clientName = "Hypnotic", clientVersion = "r1005", prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.gray + ": ";
	
	public void startup() {	
		setmgr = new SettingsManager();
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		clickgui = new ClickGUI();	
		fm = new FontManager();
		commandManager = new CommandManager();
		hud = new HUD();
		saveload = new SaveLoad();
		Display.setTitle(clientName + " " + clientVersion);
		discordRP.title = this.rptitle();
		discordRP.start();
	}
	
	public void shutdown() {
		discordRP.shutdown();
		saveload.save();
	}
	
	public void onKeyPress(int key) {
		for(Mod m : moduleManager.getModules()) {
			if(m.getKey() == key) {
				m.toggle();
			}
		}
	}
	
	public boolean onSendChatMessage(String s){
			if(s.startsWith(".")){
				commandManager.callCommand(s.substring(1));
			}
			return false;
		}
	public DiscordRP getDiscordRP() {
		return discordRP;
	}
	public String rptitle() 
	{
		switch(r.nextInt(10)) 
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
			default: return "Default Text";
		}
	}
	
}
