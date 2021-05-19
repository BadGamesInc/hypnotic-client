package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.Wrapper;

public class AutoConfig extends Mod {

	//public SettingsManager Hypnotic.instance.setmgr = new SettingsManager();
	//public ModuleManager Hypnotic.instance.moduleManager = new ModuleManager();
	public Setting server;
	
	public AutoConfig() {
		super("AutoConfig", 0, Category.MISC, "Automatically configure your settings for a specific server");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Redesky ");
		options.add("No Anticheat");
		Hypnotic.instance.setmgr.rSetting(server = new Setting("Server", this, "Redesky ", options));
	}
	
	@Override
	public void onEnable() {
		Wrapper.tellPlayer("Configuring for " + server.getValString() + "...");
		if(server.getValString().equalsIgnoreCase("Redesky ")) {
			Hypnotic.instance.setmgr.getSettingByName("Scaffold Mode").setValString("Redesky");
			Hypnotic.instance.setmgr.getSettingByName("Flight Mode").setValString("Redesky Fly");
			Hypnotic.instance.setmgr.getSettingByName("Boost").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Tower").setValBoolean(false);
			Hypnotic.instance.setmgr.getSettingByName("TowerMove").setValBoolean(false);
			Hypnotic.instance.setmgr.getSettingByName("Range").setValDouble(5.29);
			Hypnotic.instance.setmgr.getSettingByName("APS").setValDouble(14);
			Hypnotic.instance.setmgr.getSettingByName("AutoBlock").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Players").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").setValString("None");
			Hypnotic.instance.setmgr.getSettingByName("Speed Mode").setValString("Redesky LongJump");
		} else if(server.getValString().equalsIgnoreCase("No Anticheat")) {
			Hypnotic.instance.setmgr.getSettingByName("Scaffold Mode").setValString("Redesky");
			Hypnotic.instance.setmgr.getSettingByName("Boost").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Tower").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("TowerMove").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Range").setValDouble(6);
			Hypnotic.instance.setmgr.getSettingByName("AutoBlock").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Players").setValBoolean(true);
			Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").setValString("Silent");
			Hypnotic.instance.setmgr.getSettingByName("Flight Mode").setValString("Velocity");
			Hypnotic.instance.setmgr.getSettingByName("Flight Speed").setValDouble(4.5);
			Hypnotic.instance.setmgr.getSettingByName("APS").setValDouble(20);
			Hypnotic.instance.setmgr.getSettingByName("Speed Mode").setValString("Bhop2");
			Hypnotic.instance.setmgr.getSettingByName("Horizontal").setValDouble(0);
			Hypnotic.instance.setmgr.getSettingByName("Vertical").setValDouble(0);
			Hypnotic.instance.setmgr.getSettingByName("Reach").setValDouble(6);
		}
		
		Wrapper.tellPlayer("Done!");
		this.toggle();
	}

}
