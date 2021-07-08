package badgamesinc.hypnotic.module;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.CustomHotbar;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGuiMod;
import badgamesinc.hypnotic.module.combat.*;
import badgamesinc.hypnotic.module.gui.*;
import badgamesinc.hypnotic.module.misc.*;
import badgamesinc.hypnotic.module.movement.*;
import badgamesinc.hypnotic.module.player.*;
import badgamesinc.hypnotic.module.render.*;
import badgamesinc.hypnotic.module.render.wings.Wings;
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
	
	public CopyOnWriteArrayList<Mod> getDisplayModules() {
		CopyOnWriteArrayList<Mod> enabledModules = new CopyOnWriteArrayList<>();
		for(Mod m : getModules()) {
			enabledModules.add(m);
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
	
	//Movement
	public ClickGuiMod clickGui = new ClickGuiMod();
	public Flight flight = new Flight();
	public Sprint sprint = new Sprint();
	public LadderLauncher ladderLauncher = new LadderLauncher();
	public Fast fast = new Fast();
	public Speed speed = new Speed();
	public AirJump airJump = new AirJump();
	public Spider spider = new Spider();
	public InventoryMove invMove = new InventoryMove();
	public Phase phase = new Phase();
	public Longjump longjump = new Longjump();
	public Step step = new Step();
	public TargetStrafe targetStrafe = new TargetStrafe();
	
	//Render
	public Fullbright fb = new Fullbright();
	public BlockAnimations ba = new BlockAnimations();
	public ItemCustomization itemCustom = new ItemCustomization();
	public TargetHUD targetHud = new TargetHUD();
	public ESP esp = new ESP();
	public Cape cape = new Cape();
	public Chams chams = new Chams();
	public NameTags nameTags = new NameTags();
	public ItemPhysics itemPhys = new ItemPhysics();
	public NoRender noRender = new NoRender();
	public Glint glint = new Glint();
	public Wings wings = new Wings();
	
	//Player
	public NoFall noFall = new NoFall();
	public Jesus jesus = new Jesus();
	public NoSlow noSlow = new NoSlow();
	public InventoryManager invManag = new InventoryManager();
	public AutoArmor autoArmor = new AutoArmor();
	public SafeWalk safeWalk = new SafeWalk();
	public Scaffold scaffold = new Scaffold();
	
	//Combat
	public KillAura ka = new KillAura();
	public Velocity vel = new Velocity();
	public Reach reach = new Reach();
	public AutoPot autoPot = new AutoPot();
	public AutoGapple autoGap = new AutoGapple();
	public AntiBot antiBot = new AntiBot();
	public Criticals crits = new Criticals();
	
	//Misc
	public ChatSpammer chatSpammer = new ChatSpammer();
	public PizzaHutWY phWY = new PizzaHutWY();
	public RetardDetector retard = new RetardDetector();
	public Disabler disabler = new Disabler();
	public PCPinger pcpinger = new PCPinger();
	public ChestStealer stealer = new ChestStealer();
	public FastEat fastEat = new FastEat();
	public AutoL l = new AutoL();
	public AutoConfig autoCfg = new AutoConfig();
	public KillSults killSults = new KillSults();
	public PingSpoof pingSpoof = new PingSpoof();
	public Blink blink = new Blink();
	
	//World
	public FastBreak klsdfjlsk = new FastBreak();
	public FastPlace lksf = new FastPlace();
	public Timer timer = new Timer();
	public Nuker nuker = new Nuker();
	public BedNuker bedNuker = new BedNuker();
	
	//GUI
	public ArrayListModule arrayMod = new ArrayListModule();
	public InfoHud infoHud = new InfoHud();
	public Logo logo = new Logo();
	public CustomHotbar custHb = new CustomHotbar();
	public Keystrokes keystrokes = new Keystrokes();
	public PureGaming pureGaming = new PureGaming();
	
	
 	private void movement() {
		modules.add(flight);
		modules.add(sprint);
		modules.add(ladderLauncher);
		modules.add(fast);
		modules.add(speed);
		modules.add(airJump);
		modules.add(spider);
		modules.add(invMove);
		modules.add(phase);
		modules.add(longjump);
		modules.add(step);
		modules.add(targetStrafe);
	}
	
	private void render() {
		modules.add(fb);
		modules.add(ba);
		modules.add(itemCustom);
		modules.add(targetHud);
		modules.add(esp);
		modules.add(cape);
		modules.add(chams);
		modules.add(nameTags);
		modules.add(itemPhys);
		//TODO: Fix these
		modules.add(new Tracers());
		modules.add(noRender);		
		modules.add(glint);
		modules.add(wings);
		modules.add(new BrightPlayer());
		modules.add(new ChestESP());
		modules.add(new Sigma());
		modules.add(new TimeChanger());
	}
	
	private void player() {
		modules.add(noFall);
		modules.add(jesus);
		modules.add(noSlow);
		modules.add(invManag);
		modules.add(autoArmor);
		modules.add(safeWalk);
		modules.add(scaffold);
		modules.add(new Freecam());
	}
	
	private void combat() {
		modules.add(ka);
		modules.add(vel);
		modules.add(reach);
		modules.add(autoPot);
		modules.add(autoGap);
		modules.add(antiBot);
		//TODO: Fix these
		//modules.add(new BowAimbot());
		modules.add(crits);
		modules.add(new TPAura());
	}
	
	private void misc() {
		modules.add(chatSpammer);
		modules.add(phWY);
		modules.add(retard);
		modules.add(disabler);
		modules.add(pcpinger);
		modules.add(stealer);
		modules.add(fastEat);
		modules.add(l);
		//modules.add(autoCfg);
		modules.add(killSults);
		modules.add(pingSpoof);
		modules.add(blink);
		modules.add(new AntiBan());
	}
	
	private void world() {
		modules.add(klsdfjlsk);
		modules.add(lksf);
		modules.add(timer);
		modules.add(nuker);
		modules.add(bedNuker);
		modules.add(new FakePlayer());
	}
	
	private void gui() {
		modules.add(arrayMod);
		modules.add(infoHud);
		modules.add(logo);
		modules.add(custHb);
		modules.add(keystrokes);
		modules.add(pureGaming);
		modules.add(new Music());
	}
	
	public ModuleManager() {
		//ClickGUI
		modules.add(clickGui);
		
		//Other junk
		movement();
		render();
		player();
		combat();
		misc();
		world();
		gui();
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
