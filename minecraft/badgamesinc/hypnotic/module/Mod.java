package badgamesinc.hypnotic.module;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import net.minecraft.client.Minecraft;

public class Mod {

	protected Minecraft mc = Minecraft.getMinecraft();
	private int key;
	private boolean enabled;
	private String name, displayName;
	private Category category;
	
	public Mod(String name, int key, Category category) {
		this.name = name;
		this.key = key;
		this.category = category;
		enabled = false;
		displayName = name;
		setup();
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
	}
	
	public void onUpdate() {}
	public void onEnable() {Hypnotic.instance.eventManager.register(this);}
	public void onDisable() {Hypnotic.instance.eventManager.unregister(this);}
	public void setup() {}
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
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
