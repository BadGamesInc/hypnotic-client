package badgamesinc.hypnotic;

import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.event.EventManager;
import badgamesinc.hypnotic.gui.HUD;
import badgamesinc.hypnotic.gui.clickgui.ClickGUI;
import badgamesinc.hypnotic.gui.clickgui.settings.SettingsManager;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;

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
	public static final String clientName = "Hypnotic", clientVersion = "b1", prefix = ColorUtils.purple + Hypnotic.instance.clientName + ColorUtils.reset + ": " + ColorUtils.gray;
	
	public void startup() {
		setmgr = new SettingsManager();
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		clickgui = new ClickGUI();
		hud = new HUD();
		fm = new FontManager();
		commandManager = new CommandManager();
		saveload = new SaveLoad();
		Display.setTitle(clientName + " - " + clientVersion);
	}
	
	public void shutdown() {

	}
	
	public void onKeyPress(int key) {
		for(Mod m : moduleManager.getModules()) {
			if(m.getKey() == key) {
				m.toggle();
			}
		}
	}
	
	public boolean onSendChatMessage(String s){//EntityPlayerSP
			if(s.startsWith(".")){
				commandManager.callCommand(s.substring(1));
			}
			return false;
		}
	
}
