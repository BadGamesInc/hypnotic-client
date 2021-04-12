package badgamesinc.hypnotic.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.client.Minecraft;

public class Mod {

	protected static Minecraft mc = Minecraft.getMinecraft();
	private int key;
	private boolean enabled;
	private String name;
	public String displayName;
	private String description;
	private Category category;
	
	private long currentMS = 0L;
	protected long lastMS = -1L;
	
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
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
		}
	}
	
	public void onUpdate() {}
	public void onEnable() {
		Hypnotic.instance.eventManager.register(this);
	}
	public void onDisable() {
		Hypnotic.instance.eventManager.unregister(this);
		RenderUtils.resetPlayerPitch();
		RenderUtils.resetPlayerYaw();
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
	
	public final void updateMS()
	{
		currentMS = System.currentTimeMillis();
	}
	
	public final void updateLastMS()
	{
		lastMS = System.currentTimeMillis();
	}
	
	public final boolean hasTimePassedM(long MS)
	{
		return currentMS >= lastMS + MS;
	}
	
	public final boolean hasTimePassedS(float speed)
	{
		return currentMS >= lastMS + (long)(1000 / speed);
	}

}
