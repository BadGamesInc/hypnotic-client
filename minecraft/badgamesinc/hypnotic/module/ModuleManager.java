package badgamesinc.hypnotic.module;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGuiMod;
import badgamesinc.hypnotic.module.combat.AntiBot;
import badgamesinc.hypnotic.module.combat.AutoGapple;
import badgamesinc.hypnotic.module.combat.AutoPot;
import badgamesinc.hypnotic.module.combat.Criticals;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.Reach;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.module.combat.Velocity;
import badgamesinc.hypnotic.module.gui.ArrayListModule;
import badgamesinc.hypnotic.module.gui.InfoHud;
import badgamesinc.hypnotic.module.gui.Logo;
import badgamesinc.hypnotic.module.misc.AutoCaptcha;
import badgamesinc.hypnotic.module.misc.AutoConfig;
import badgamesinc.hypnotic.module.misc.AutoL;
import badgamesinc.hypnotic.module.misc.AutoSave;
import badgamesinc.hypnotic.module.misc.ChatSpammer;
import badgamesinc.hypnotic.module.misc.ChestStealer;
import badgamesinc.hypnotic.module.misc.Disabler;
import badgamesinc.hypnotic.module.misc.FastEat;
import badgamesinc.hypnotic.module.misc.KillSults;
import badgamesinc.hypnotic.module.misc.PCPinger;
import badgamesinc.hypnotic.module.misc.PingSpoof;
import badgamesinc.hypnotic.module.misc.PizzaHutWY;
import badgamesinc.hypnotic.module.misc.RetardDetector;
import badgamesinc.hypnotic.module.movement.AirJump;
import badgamesinc.hypnotic.module.movement.Fast;
import badgamesinc.hypnotic.module.movement.Flight;
import badgamesinc.hypnotic.module.movement.InventoryMove;
import badgamesinc.hypnotic.module.movement.LadderLauncher;
import badgamesinc.hypnotic.module.movement.Longjump;
import badgamesinc.hypnotic.module.movement.Phase;
import badgamesinc.hypnotic.module.movement.Speed;
import badgamesinc.hypnotic.module.movement.Spider;
import badgamesinc.hypnotic.module.movement.Sprint;
import badgamesinc.hypnotic.module.movement.Step;
import badgamesinc.hypnotic.module.player.AutoArmor;
import badgamesinc.hypnotic.module.player.InventoryManager;
import badgamesinc.hypnotic.module.player.Jesus;
import badgamesinc.hypnotic.module.player.NoFall;
import badgamesinc.hypnotic.module.player.NoSlow;
import badgamesinc.hypnotic.module.player.SafeWalk;
import badgamesinc.hypnotic.module.player.Scaffold;
import badgamesinc.hypnotic.module.render.BlockAnimations;
import badgamesinc.hypnotic.module.render.Cape;
import badgamesinc.hypnotic.module.render.Chams;
import badgamesinc.hypnotic.module.render.ESP;
import badgamesinc.hypnotic.module.render.Fullbright;
import badgamesinc.hypnotic.module.render.ItemCustomization;
import badgamesinc.hypnotic.module.render.ItemPhysics;
import badgamesinc.hypnotic.module.render.NameTags;
import badgamesinc.hypnotic.module.render.NoRender;
import badgamesinc.hypnotic.module.render.TargetHUD;
import badgamesinc.hypnotic.module.world.BedNuker;
import badgamesinc.hypnotic.module.world.FastBreak;
import badgamesinc.hypnotic.module.world.FastPlace;
import badgamesinc.hypnotic.module.world.Nuker;
import badgamesinc.hypnotic.module.world.Timer;

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
		modules.add(new ClickGuiMod());
		
		//Movement
		modules.add(new Flight());
		modules.add(new Sprint());
		modules.add(new LadderLauncher());
		modules.add(new Fast());
		modules.add(new Speed());
		modules.add(new AirJump());
		modules.add(new Spider());
		modules.add(new InventoryMove());
		modules.add(new Phase());
		modules.add(new Longjump());
		modules.add(new Step());
		
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
		//TODO: Fix these
		//modules.add(new Tracers());
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
		modules.add(new AntiBot());
		//TODO: Fix these
		//modules.add(new BowAimbot());
		modules.add(new Criticals());
		
		//Misc
		modules.add(new ChatSpammer());
		modules.add(new PizzaHutWY());
		modules.add(new RetardDetector());
		modules.add(new Disabler());
		modules.add(new PCPinger());
		modules.add(new ChestStealer());
		modules.add(new FastEat());
		modules.add(new AutoL());
		modules.add(new AutoSave());
		modules.add(new AutoConfig());
		modules.add(new KillSults());
		modules.add(new PingSpoof());
		modules.add(new AutoCaptcha());

		//World
		modules.add(new FastBreak());
		modules.add(new FastPlace());
		modules.add(new Timer());
		modules.add(new Nuker());
		modules.add(new BedNuker());
		
		//GUI
		modules.add(new ArrayListModule());
		modules.add(new InfoHud());
		modules.add(new Logo());
		
	}
	
	public CopyOnWriteArrayList<Mod> getModules() {
		return modules;
	}
	
	public void registerModule(Mod m) {
		modules.add(m);
	}
	
	public ArrayList<Mod> getModulesInCategory(Category category){
		ArrayList<Mod> categoryModules = new ArrayList<>();
		for(Mod m : modules){
		    if (m.getCategory() == category){
			categoryModules.add(m);
		    }
		}
		return categoryModules;
    	}
	
	public <T extends Mod> T getModule(Class<T> clazz) {
        	return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    	}
	
	
}
