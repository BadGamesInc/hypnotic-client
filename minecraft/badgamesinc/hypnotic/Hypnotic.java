package badgamesinc.hypnotic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.config.ConfigManager;
import badgamesinc.hypnotic.config.SaveLoad;
import badgamesinc.hypnotic.config.friends.FriendManager;
import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.gui.altmanager.FileManager;
import badgamesinc.hypnotic.gui.altmanager.GuiAltManager;
import badgamesinc.hypnotic.gui.music.FileDownloader;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Logger;
import badgamesinc.hypnotic.util.Logger.LogType;
import badgamesinc.hypnotic.util.drm.DRM;
import badgamesinc.hypnotic.util.drm.Level3Checks;
import badgamesinc.hypnotic.util.drm.Level4Checks;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.enchantment.EnchantmentManager;
import net.minecraft.util.ResourceLocation;
import thealtening.auth.TheAlteningAuthentication;
import thealtening.auth.service.AlteningServiceType;

public class Hypnotic {

	public static final Hypnotic instance = new Hypnotic();
	public static String APIKey = "";
	public static String func_1065893_a = "https://github.com/BadGamesInc/hypnotic-client";
	public int l3 = 0;
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public FriendManager friendManager;
	public HUD hud;
	public CommandManager commandManager;
	public static TheAlteningAuthentication auth;
	public NotificationManager notificationManager;
	public static HashMap<String, ResourceLocation> cachedImages = new HashMap<>();
	public FileManager fileManager;
	public SaveLoad saveload;
	public GuiAltManager guiAltLogin;
	public DiscordRP discordRP;
	public FontManager fm;
	public static ConfigManager cfgManager = new ConfigManager();
	public EnchantmentManager enchantmentManager;																																																																																																																																																																												/* REQUIRED TO PREVENT A CRASH DO NOT REMOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FuelPumps On Top */ 
	private Random r = new Random();																																																																																																																																																																					                  										private double superSecretKey = this.clientName == "Hypnotic" ? Double.MAX_VALUE - 0.00000001 : Byte.MIN_VALUE;																																																																																										
	
	public static final String clientName = "Hypnotic", clientVersion = "r1007", fullName = clientName + "-" + clientVersion, prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.white + ": ", hypnoticDir = new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic").toString();
	public String latestBuild = "", currentBuild = clientVersion;
	public boolean outdated, finishedInstall;
	
	public void startup() {	
		Logger.log("Setting the title...", "", LogType.INFO);
		Display.setTitle(clientName + " " + clientVersion);
		fm = new FontManager();
		Logger.log("Initializing Modules...", "ModuleManager", LogType.INFO);
		moduleManager = new ModuleManager();
		Logger.log("Initializing Events...", "EventManager", LogType.INFO);
		eventManager = new EventManager();
		Logger.log("Initializing Commands...", "CommandManager", LogType.INFO);
		commandManager = new CommandManager();
		Logger.log("Initializing Friends...", "FriendManager", LogType.INFO);
		friendManager = new FriendManager();
		Logger.log("Initializing Altening Service...", "", LogType.INFO);
		auth = new TheAlteningAuthentication(AlteningServiceType.MOJANG);
		Logger.log("Initializing Notifications...", "NotificationManager", LogType.INFO);
		notificationManager = new NotificationManager();
		Logger.log("Initializing HUD...", "", LogType.INFO);
		hud = new HUD();
		Logger.log("Initializing File Systems...", "", LogType.INFO);
		this.fileManager = new FileManager();
		saveload = new SaveLoad();
		this.guiAltLogin = new GuiAltManager(new GuiMainMenu());
		Logger.log("Initializing Discord Rich Pressence...", "", LogType.INFO);
		discordRP = new DiscordRP();
		enchantmentManager = new EnchantmentManager();
		eventManager.register(this);
		discordRP.start();
		discordRP.title = this.rptitle();
		getAPIKey();
		DRM.checkDRM();
		Logger.log("Checking for any form of skid...", "MikeGaming", LogType.INFO);
		Level3Checks.setL3();																																																																												if(l3 == 0) { System.err.println("[HYPNOTIC SKID DETECTION]: CLASS 3 LEVEL SKID SENSORS TRIGGERED"); System.err.println("mike gaming is not happy with you :("); Minecraft.getMinecraft().displayGuiScreen(null); }
		Level4Checks.checkLevel4();
		
		Logger.log("Loading configs...", "ConfigManager", LogType.INFO);
		if (cfgManager.config.exists()) {
            cfgManager.loadConfig();
        }

		Logger.log("Starting Config Daemon...", "ConfigManager", LogType.INFO);
        Thread configDaemon = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cfgManager.saveConfig();
            }
        });
        configDaemon.setDaemon(true);
        configDaemon.start();
		
        Logger.log("Caching Images...", "", LogType.INFO);
        
		for (Type notType : Type.values()) {
			for (Color notColor : Color.values()) {	
				cachedImages.put("hypnotic/textures/notifications/" + notType.filePrefix + notColor.fileSuffix + ".png", new ResourceLocation("hypnotic/textures/notifications/" + notType.filePrefix + notColor.fileSuffix + ".png"));	
			}
		}
		
		for (ResourceLocation resource : cachedImages.values()) {
			String name = resource.getResourcePath();
		}
		
		KillAura.target = null;
		moduleManager.blink.setEnabled(false);
		
		checkVersion();
		Logger.log("Finished Hypnotic initialization!", "", LogType.INFO);
	}
	
	public void checkVersion() {
		Logger.log("Getting latest build version", "", LogType.INFO);
		try {
			URL pasteLink = new URL("https://pastebin.com/raw/1NnsBeaj");
			BufferedReader br = new BufferedReader(new InputStreamReader(pasteLink.openStream()));
			this.latestBuild = br.readLine().trim();
			br.close();
			Logger.log("Current build is " + this.latestBuild, "", LogType.INFO);
			if (!this.latestBuild.contains(this.clientVersion)) {
				this.setOutdated(true);		
				Logger.log("You are on an outdated build!", "Version Checker", LogType.WARNING);
				Logger.log("Please download the latest build from www.github.com/badgamesinc/hypnotic-client", "Version Checker", LogType.WARNING);
				Display.setTitle(this.fullName + " (Outdated)");
			} else {
				this.setOutdated(false);
				Logger.log("Your build is up to date!", "", LogType.INFO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isFinishedInstall() {
		return finishedInstall;
	}

	public void setFinishedInstall(boolean finishedInstall) {
		this.finishedInstall = finishedInstall;
	}
	
	public boolean isOutdated() {
		return outdated;
	}

	public void setOutdated(boolean outdated) {
		this.outdated = outdated;
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
	}																																																																																																																																																																																																											private void getAPIKey() { if (this.superSecretKey != Double.MAX_VALUE - 0.00000001) {System.err.println("[HYPNOTIC SKID DETECTION] " + this.clientName + " isn't hypnotic, nice try skid"); Minecraft.getMinecraft().displayGuiScreen(null); this.moduleManager = null; this.commandManager = null; this.commandManager = null;}}																																																																																																																																																																																																																			
}																																																																																																																																																						
