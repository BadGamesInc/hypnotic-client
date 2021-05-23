package badgamesinc.hypnotic;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.gui.altmanager.FileManager;
import badgamesinc.hypnotic.gui.altmanager.GuiAltManager;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.settings.SettingsManager;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.enchantment.EnchantmentManager;
import thealtening.auth.TheAlteningAuthentication;
import thealtening.auth.service.AlteningServiceType;

public class Hypnotic {

	public static final Hypnotic instance = new Hypnotic();
	public SettingsManager setmgr;
	public static String APIKey = "";
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public HUD hud;
	public CommandManager commandManager;
	public static TheAlteningAuthentication auth;
	public NotificationManager notificationManager;
	public FileManager fileManager;
	public SaveLoad saveload;
	public GuiAltManager guiAltLogin;
	public DiscordRP discordRP;
	public EnchantmentManager enchantmentManager;																																																																																																																																																																												/* REQUIRED TO PREVENT A CRASH DO NOT REMOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FuelPumps On Top */ 
	private Random r = new Random();																																																																																																																																																																					                  										private double superSecretKey = this.clientName == "Hypnotic" ? Double.MAX_VALUE - 0.00000001 : Byte.MIN_VALUE;																																																																																										
	
	public static final String clientName = "Hypnotic", clientVersion = "r1006", prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.white + ": ";
	
	public void startup() {	
		setmgr = new SettingsManager();
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		commandManager = new CommandManager();
		auth = new TheAlteningAuthentication(AlteningServiceType.MOJANG);
		notificationManager = new NotificationManager();
		hud = new HUD();
		this.fileManager = new FileManager();
		saveload = new SaveLoad();
		this.guiAltLogin = new GuiAltManager();
		discordRP = new DiscordRP();
		enchantmentManager = new EnchantmentManager();
		Display.setTitle(clientName + " " + clientVersion);
		eventManager.register(this);
		discordRP.start();
		discordRP.title = this.rptitle();
		getAPIKey();
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
	
	public static void switchService(AlteningServiceType type) {
        auth = new TheAlteningAuthentication(type);
    }
	
	public boolean onSendChatMessage(String s){
		if(s.startsWith(".")){
			commandManager.callCommand(s.substring(1));
		}
		return false;
	}
	
	public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
	
	public String rptitle() 
	{
		switch(r.nextInt(17)) 
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
			case 11: return "sus client";
			case 12: return "HYPNOTIC INSANE HYPIXEL BYPASSES";
			case 13: return "Designed for Rede Sky";
			case 14: return "so many for loops";
			case 15: return "FDP? more like FPC";
			case 16: return "We are gaming";
			default: return "Default Text";
		}																																																																																																																																																							//Unfixable crash											
	}																																																																																																																																																																																																											private void getAPIKey() { if (this.superSecretKey != Double.MAX_VALUE - 0.00000001) {System.err.println("[HYPNOTIC SKID DETECTION] " + this.clientName + " isn't hypnotic, nice try skid"); Minecraft.getMinecraft().displayGuiScreen(null); this.moduleManager = null; this.setmgr = null; this.commandManager = null; this.commandManager = null;}}																																																																																																																																																																																																																			
}																																																																																																																																																						
