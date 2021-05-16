package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.gui.newerclickgui.button.Button;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;

public class SettingsWindow {
    int x = 180;
    int y = 80;
    Button parent;
    ArrayList<Component> components = new ArrayList<>();
    ArrayList<Setting> settings;
    int yCount = 0;
    public static GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 20, false, false, false);
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
    public SettingsWindow(Button parent) {
        yCount = 0;
        this.parent = parent;
        int count = 1;
        int xCount = 0;
        if (Hypnotic.instance.setmgr.getSettingsByMod(parent.mod) != null) {
        	settings = Hypnotic.instance.setmgr.getSettingsByMod(parent.mod);
        }
        if (settings != null) {
	        for(Setting set : settings){
	        	
		            if(set.isCheck()){
		                this.components.add(new CheckBox(x + 20 + xCount, y + 10 + count * 22, set, this));
		
		            }else if(set.isCombo()){
		                this.components.add(new ComboBox(x + 20 + xCount, y + 10 + count * 22, set, this));
		
		            }else if(set.isSlider()){
		                this.components.add(new Slider(x + 20 + xCount, y + 10 + count * 22, set, this));
		
		            }
	        	
		            	count++;
	
	        }
        }
        
    }

    public void draw(int mouseX, int mouseY){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.drawRoundedRect(x, y, sr.getScaledWidth() - x, sr.getScaledHeight() - y, 5, new Color(48, 48, 48, 255));

        for(Component c : components){
        	if (!components.isEmpty())
        		c.draw(mouseX, mouseY);
        }
        bigFontRenderer.drawString(parent.mod.getDescription(), x + 5, y + 5, -1, true);
    }

    public void mouseClicked(int mouseX, int mouseY){
    	if (settings == null) {
    		return;
    	}
        for(Component c : components){
            c.mouseClicked(mouseX, mouseY);
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
