package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import java.awt.Color;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.button.Button;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.KeybindSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SettingsWindow {
    int x = 180;
    int y = 80;
    Button parent;
    ArrayList<Component> components = new ArrayList<>();
    ArrayList<Setting> settings;
    int yCount;
    public static GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 20, false, false, false);
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
    public static GlyphPageFontRenderer smallBigFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 16, false, false, false);
    public static GlyphPageFontRenderer smallFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 14, false, false, false);
    
    public SettingsWindow(Button parent) {
        yCount = 0;
        this.parent = parent;
        int count = 1;
        int xCount = 0;
        if (parent.mod.getSettings() != null) {
        	settings = parent.mod.getSettings();
        }
        int guiScaleMultiplier = 22;
        if (Minecraft.getMinecraft().gameSettings.guiScale > 2) {
        	guiScaleMultiplier = 14;
        	x = 155;
        	y = 55;
        }
        for (Component c : components) {
        	//components.add(new Keybind(x + 20, y + 10, null, this));
        }
        if (settings != null) {
	        for(Setting set : settings){
	        	
	        	if(set instanceof KeybindSetting){
	        		//components.add(new Keybind(x + bigFontRenderer.getStringWidth(parent.mod.getDescription() + 50), y + 10, set, this));
	        		count--;
	        	}
		            if(set instanceof BooleanSetting){
		                this.components.add(new CheckBox(x + 20 + xCount, y + 10 + count * guiScaleMultiplier, set, this));
		
		            }else if(set instanceof ModeSetting){
		                this.components.add(new ComboBox(x + 20 + xCount, y + 10 + count * guiScaleMultiplier, set, this));
		
		            }else if(set instanceof NumberSetting){
		                this.components.add(new Slider(x + 20 + xCount, y + 10 + count * guiScaleMultiplier, set, this));
		
		            }
	        	
		            if (count < 14) {
		            	count++;
		            } else {
		            	count = 1;
		            	xCount+=210;
		            }
	
	        }
        }
        
    }

    public void draw(int mouseX, int mouseY){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.drawRoundedRect(x, y, sr.getScaledWidth() - x, sr.getScaledHeight() - y, 10, new Color(48, 48, 48, 255));

        for(Component c : components){
        	if (!components.isEmpty())
        		c.draw(mouseX, mouseY);
        }
        if (Minecraft.getMinecraft().gameSettings.guiScale <= 2)
        	bigFontRenderer.drawString(parent.mod.getDescription(), x + 5, y + 5, -1, true);
        else
        	smallBigFontRenderer.drawString(parent.mod.getDescription(), x + 5, y + 5, -1, true);
    }

    public void mouseClicked(int mouseX, int mouseY){
    	if (settings == null) {
    		return;
    	}
        for(Component c : components){
            c.mouseClicked(mouseX, mouseY);
        }
    }
    
    public void keyTyped(char typedChar, int keyCode) {
    	if (Keybind.listening) {
    		
    	}
    }

    public void mouseReleased(){
        for(Component c : components){
            c.mouseRelease();
        }
    }

    public Button getParent() {
        return parent;
    }

    public void setParent(Button parent) {
        this.parent = parent;
    }
}
