package badgamesinc.hypnotic.module;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.example.GUI;
import badgamesinc.hypnotic.module.combat.*;
import badgamesinc.hypnotic.module.movement.*;
import badgamesinc.hypnotic.module.player.*;
import badgamesinc.hypnotic.module.render.*;

public class ModuleManager {

	public CopyOnWriteArrayList<Mod> modules = new CopyOnWriteArrayList<>();
	
	public ModuleManager() {
		//ClickGUI
		modules.add(new GUI());
		
		//Movement
		modules.add(new Flight());
		modules.add(new Sprint());
		
		//Render
		modules.add(new HUDModule());
		modules.add(new Fullbright());
		modules.add(new BlockAnimations());
		
		//Player
		modules.add(new NoFall());
		
		//Combat
		modules.add(new KillAura());
		
		//Misc
		modules.add(new ChatSpammer());

		//World
	}
	
	public CopyOnWriteArrayList<Mod> getModules() {
		return modules;
	}
	
	public void registerModule(Mod m) {
		modules.add(m);
	}
	
	public CopyOnWriteArrayList<Mod> getEnabledModules() {
		CopyOnWriteArrayList<Mod> enabledModules = new CopyOnWriteArrayList<>();
		for(Mod m : getModules()) {
			if(m.isEnabled()) {
				enabledModules.add(m);
			}
		}
		return enabledModules;
	}
	
	public static Mod getModuleByName(String moduleName) {
		for(Mod mod : Hypnotic.instance.moduleManager.getModules()) {
			if ((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}	
}
