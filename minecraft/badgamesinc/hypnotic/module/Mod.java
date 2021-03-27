package badgamesinc.hypnotic.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import badgamesinc.hypnotic.Hypnotic;
import net.minecraft.client.Minecraft;

public class Mod {

	protected Minecraft mc = Minecraft.getMinecraft();
	private int key;
	private boolean enabled;
	private String name, displayName, description;
	private Category category;
	
	public Mod(String name, int key, Category category, String description) {
		this.name = name;
		this.key = key;
		this.category = category;
		this.description = description;
		enabled = false;
		displayName = name;
		setup();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void toggle() {
		enabled = !enabled;
		if(enabled) {
			onEnable();
		} else {
			onDisable();
		}
		if(Hypnotic.instance.saveload != null) {
			Hypnotic.instance.saveload.save();
		}
		
		if(Hypnotic.instance.setmgr.getSettingByName("Sound").getValBoolean()) {
			mc.thePlayer.playSound("random.click", 10.5f, 100.5f);
		}
	}
	
	public void onUpdate() {}
	public void onEnable() {
		Hypnotic.instance.eventManager.register(this);
	}
	public void onDisable() {
		Hypnotic.instance.eventManager.unregister(this);
	}
	public void setup() {}
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		if(Hypnotic.instance.saveload != null) {
			Hypnotic.instance.saveload.save();
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		
		this.enabled = enabled;
		if(Hypnotic.instance.saveload != null) {
			Hypnotic.instance.saveload.save();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
