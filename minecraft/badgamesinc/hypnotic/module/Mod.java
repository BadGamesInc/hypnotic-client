package badgamesinc.hypnotic.module;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.config.ConfigSetting;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.KeybindSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.SoundUtil;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Mod {

	protected static Minecraft mc = Minecraft.getMinecraft();
	public String displayName;
	private String description;
	private Category category;
	private TimeHelper timer = new TimeHelper();
	public ArrayList<Setting> settings = new ArrayList<>();
	@Expose
    @SerializedName("key")
	public int keyCode;
	@Expose
    @SerializedName("enabled")
	public boolean enabled;
	@Expose
    @SerializedName("name")
	public String name;
	@Expose
    @SerializedName("settings")
    public ConfigSetting[] cfgSettings;
	private KeybindSetting keyBind = new KeybindSetting("Keybind: ", 0);

	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	public FontRenderer fr = mc.fontRendererObj;
	
	private long currentMS = 0L;
	protected long lastMS = -1L;
	
	public float mSize;
    public float lastSize;
    
    public long start = 0;
	
	public Mod(String name, int key, Category category, String description) {
		this.name = name;
		this.keyCode = key;
		this.category = category;
		this.description = description;
		enabled = false;
		displayName = name;
	}
	
	public void addSetting(Setting setting) {
        getSettings().add(setting);
    }
	
	public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void addSettings(Setting... settings) {
        for (Setting setting : settings) {
            addSetting(setting);
        }
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
			Hypnotic.instance.eventManager.register(this);
		} else {
			onDisable();
			Hypnotic.instance.eventManager.unregister(this);
		}
		if (Hypnotic.instance.moduleManager.clickGui.sound.isEnabled()) {
			SoundUtil.playSound(isEnabled() ? "on.wav" : "off.wav");
		}
	}
	
	public void onUpdate() {}
	public void onEnable() {
		Hypnotic.instance.eventManager.register(this);
		
			if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
				mSize = 0;
	        	lastSize = fontRenderer.getStringWidth(this.getDisplayName());
			} else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
				mSize = 0;
		        lastSize = fr.getStringWidth(this.getDisplayName());
			}
		Hypnotic.instance.notificationManager.show(new Notification(this.getName() + ColorUtils.green + " was enabled", (int) 3.5, NotificationType.INFO));
	}
	public void onDisable() {
		Hypnotic.instance.eventManager.unregister(this);
		RenderUtils.resetPlayerPitch();
		RenderUtils.resetPlayerYaw();
		
		
			if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
				mSize = fontRenderer.getStringWidth(this.getDisplayName());
		        lastSize =0;
			} else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
				mSize = fr.getStringWidth(this.getDisplayName());
		        lastSize =0;
			}
		Hypnotic.instance.notificationManager.show(new Notification(this.getName() + ColorUtils.red + " was disabled", (int) 3.5, NotificationType.INFO));
	}
	public void setup() {}
	
	public int getKey() {
		return keyCode;
	}

	public void setKey(int key) {
		this.keyCode = key;
		if(Hypnotic.instance.saveload != null) {
			Hypnotic.instance.saveload.save();
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		
		if (enabled){
			if (Hypnotic.instance.eventManager != null)
	            Hypnotic.instance.eventManager.register(this);
				
			if (Setting.class != null) {
				//if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
					mSize = 0;
	            	lastSize = fontRenderer.getStringWidth(this.getDisplayName());
				//} else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
					//mSize = 0;
	            	//lastSize = fr.getStringWidth(this.getDisplayName());
				//}
			}
        } else {
        	if (Hypnotic.instance.eventManager != null)
            Hypnotic.instance.eventManager.unregister(this);
        	
        	//if (Setting.class != null) {
	        	//if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
	        		mSize = fontRenderer.getStringWidth(this.getDisplayName());
	            	lastSize = 0;
	        	//} else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
	        		//mSize = fr.getStringWidth(this.getDisplayName());
	                //lastSize = 0;
	        	//}
        	//}
        }
        this.enabled = enabled;
        if(Hypnotic.instance.saveload != null){
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
