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
		modules.add(new NameTags());
		modules.add(new ItemPhysics());
		modules.add(new Tracers());
		modules.add(new NoRender());
		
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
		modules.add(new AutoGapple());
		modules.add(new TargetStrafe());
		//TODO: Fix these
		//modules.add(new BowAimbot());
		//modules.add(new Criticals());
		
		//Misc
		modules.add(new ChatSpammer());
		modules.add(new PizzaHutWY());
		modules.add(new RetardTalk());
		modules.add(new Disabler());
		modules.add(new PCPinger());
		modules.add(new ChestStealer());
		modules.add(new FastEat());
		modules.add(new AutoL());
		modules.add(new AutoSave());
		modules.add(new AutoConfig());

		modules.add(new DiscordRPC());
		modules.add(new KillSults());

		//World
		modules.add(new FastBreak());
		modules.add(new FastPlace());
		modules.add(new Timer());
		modules.add(new Nuker());
		modules.add(new BedNuker());
		
		//GUI
		modules.add(new ArrayListModule());
		modules.add(new InfoHud());
		
	}
	
	public CopyOnWriteArrayList<Mod> getModules() {
		return modules;
	}
	
	public void registerModule(Mod m) {
		modules.add(m);
	}
	
	public <T extends Mod> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }
	
	
}
