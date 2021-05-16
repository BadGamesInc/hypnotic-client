package badgamesinc.hypnotic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.module.misc.DiscordRPC;
import badgamesinc.hypnotic.settings.SettingsManager;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public class Hypnotic {

	public static final Hypnotic instance = new Hypnotic();
	public SettingsManager setmgr;
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public HUD hud;
	public CommandManager commandManager;
	public static FontManager fm;
	public SaveLoad saveload;
	public DiscordRP discordRP;
	
	public static final String clientName = "Hypnotic", clientVersion = "r1006", prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.gray + ": ";
	
	public void startup() {	
		setmgr = new SettingsManager();
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		fm = new FontManager();
		commandManager = new CommandManager();
		hud = new HUD();
		saveload = new SaveLoad();
		Display.setTitle(clientName + " " + clientVersion);
		eventManager.register(this);
		if (this.discordRP != null) {
			if (this.moduleManager.getModule(DiscordRPC.class).isEnabled()) {
				discordRP.start();
			}
		}
		
		if(saveload.configs.exists()) {
			saveload.load();
		}
	}
	
	public void shutdown() {
		
		saveload.save();
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
	
	
	
}
