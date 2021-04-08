package badgamesinc.hypnotic.module;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.example.GUI;
import badgamesinc.hypnotic.module.combat.*;
import badgamesinc.hypnotic.module.gui.*;
import badgamesinc.hypnotic.module.movement.*;
import badgamesinc.hypnotic.module.player.*;
import badgamesinc.hypnotic.module.render.*;
import badgamesinc.hypnotic.module.misc.*;
import badgamesinc.hypnotic.module.world.*;

public class ModuleManager {

	public CopyOnWriteArrayList<Mod> modules = new CopyOnWriteArrayList<>();
	
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
	
	public ModuleManager() {
		//ClickGUI
		modules.add(new GUI());
		
		//Movement
		modules.add(new Flight());
		modules.add(new Sprint());
		modules.add(new LadderLauncher());
		modules.add(new Fast());
		modules.add(new Speed());
		modules.add(new AirJump());
		modules.add(new Spider());
		modules.add(new InventoryMove());
		
		//Render
		modules.add(new Fullbright());
		modules.add(new BlockAnimations());
		modules.add(new ItemCustomization());
		modules.add(new TargetHUD());
		modules.add(new ESP());
		modules.add(new Cape());
		modules.add(new Chams());
		
		//Player
		modules.add(new NoFall());
		modules.add(new Jesus());
		modules.add(new NoSlow());
		modules.add(new InventoryManager());
		modules.add(new AutoArmor());
		modules.add(new SafeWalk());
		modules.add(new Scaffold());
		
		//Combat
		modules.add(new KillAura());
		modules.add(new Velocity());
		modules.add(new Reach());
		modules.add(new AutoPot());
		//TODO: Fix these
		//modules.add(new TargetStrafe());
		//modules.add(new BowAimbot());
		
		//Misc
		modules.add(new ChatSpammer());
		modules.add(new PizzaHutWY());
		modules.add(new RetardTalk());
		modules.add(new Disabler());
		modules.add(new PCPinger());
		modules.add(new ChestStealer());
		modules.add(new FastEat());
		modules.add(new AutoGapple());
		modules.add(new AutoL());

		//World
		modules.add(new FastBreak());
		modules.add(new FastPlace());
		modules.add(new Timer());
		//TODO: find out what the hell the minecraft song names are
		//modules.add(new MusicPlayer());
		
		//GUI
		modules.add(new ArrayListModule());
		modules.add(new HUDModule());
		
	}
	
	public CopyOnWriteArrayList<Mod> getModules() {
		return modules;
	}
	
	public void registerModule(Mod m) {
		modules.add(m);
	}
	
	
}
